package com.together.nosheng.repository;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.together.nosheng.model.pin.Pin;
import com.together.nosheng.model.plan.Plan;
import com.together.nosheng.model.project.Post;
import com.together.nosheng.model.project.Project;
import com.together.nosheng.model.user.User;
import com.together.nosheng.util.GlobalApplication;
import com.together.nosheng.viewmodel.ProjectViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectRepository {
    private FirebaseFirestore db;
    private String TAG = "ProjectRepository";

    private MutableLiveData<Map<String, Project>> userProject = new MutableLiveData<>();

    private Map<String, Project> userProjectMap;

    public ProjectRepository() {
        db = FirebaseFirestore.getInstance();
    }

    public void addUserProject(Project userProject) {
        db.collection("Project")
                .add(userProject)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.i(TAG, "Success adding document : " + documentReference.getId());

                        updateDate(documentReference.getId());

                        updateProjectList(documentReference);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public MutableLiveData<Map<String, Project>> getUserProject() {
        db.collection("User").document(GlobalApplication.firebaseUser.getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w(TAG, "addShapshotListener failed", error);
                    return;
                }
                if (value != null) {
                    userProjectMap = new HashMap<>();
                    Log.i("여기있다!", "왔나?0");
                    List<String> projectList = value.toObject(User.class).getProjectList();

                    for (String projectId : projectList) {
                        db.collection("Project").document(projectId).get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        DocumentSnapshot document = task.getResult();
                                        if (task.isSuccessful()) {
                                            if (document.exists()) {
                                                userProjectMap.put(projectId, document.toObject(Project.class));
                                                Log.i(TAG, projectId);
                                                userProject.setValue(userProjectMap);
                                            }
                                        } else {
                                            Log.i(TAG, "유저 프로젝트가 null임");
                                        }
                                    }
                                });
                    }
                }
            }
        });
        return userProject;
    }

    public MutableLiveData<Map<String, Project>> getCurrentProject(String projectId) {
        MutableLiveData<Map<String, Project>> currentProject= new MutableLiveData<>();
        db.collection("Project").document(projectId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w(TAG, "addShapshotListener failed", error);
                    return;
                }

                if (value != null && value.exists()) {
                    Map<String, Project> currenProject = new HashMap<>();
                    currenProject.put(projectId, value.toObject(Project.class));
                    currentProject.setValue(currenProject);
                }
            }
        });
        return currentProject;
    }

    public void updateUserProject(Project userProject, String projectId) {
        db.collection("Project").document(projectId)
                .set(userProject, SetOptions.merge());
    }

    public void updateProjectList(DocumentReference documentReference) {
        DocumentReference doc = db.collection("User").document(GlobalApplication.firebaseUser.getUid());
        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                User user = task.getResult().toObject(User.class);
                if (task.isSuccessful() && task.getResult().exists()) {
                    List<String> projects = user.getProjectList();
                    projects.add(documentReference.getId());

                    doc.update("projectList", projects);
                }
            }
        });
    }

    public void updateUserProjectList(List<String> projects) {
        db.collection("User").document(GlobalApplication.firebaseUser.getUid())
                .update("projectList",projects);
    }

    public void deleteMemberProject(String projectId){
        db.collection("Project").document(projectId)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Log.i(TAG, "delete proeject!");
                        }else {
                            Log.i(TAG, "delete project error!");
                        }
                    }
                });
    }

    public void deleteUserProject(String projectId) {
        db.collection("Project").document(projectId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                List<String> plans = documentSnapshot.toObject(Project.class).getPlans();
                Log.i("dddal", plans.toString());

                for (int i = 0; i < plans.size(); i++) {
                    if (i == plans.size()-1) {
                        db.collection("Plan").document(plans.get(i)).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                db.collection("Project").document(projectId)
                                        .delete()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Log.i(TAG, "delete project!");
                                                } else {
                                                    Log.i(TAG, "delete project error");
                                                }
                                            }
                                        });
                            }
                        });
                    } else {
                        db.collection("Plan").document(plans.get(i)).delete();
                    }
                }
            }
        });
    }

    public void addPost(String projectId, List<Post> posts) {
        db.collection("Project").document(projectId)
                .update("posts",posts)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Log.i(TAG, "");
                        } else {
                            Log.e(TAG, "add post error !");
                        }
                    }
                });
    }

//    public void addMember(String projectId, List<User> members) {
//        db.collection("Project").document(projectId)
//                .update("members",members)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()){
//                            Log.i(TAG, "");
//                        } else {
//                            Log.e(TAG, "add Member error !");
//                        }
//                    }
//                });
//    }

    public void addMember(String projectId, List<String> members) {
        db.collection("Project").document(projectId)
                .update("members",members)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Log.i(TAG, "");
                        } else {
                            Log.e(TAG, "add Member error !");
                        }
                    }
                });
    }

    public void addTag(String projectId, List<String> tags) {
        db.collection("Project").document(projectId)
                .update("tags",tags)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Log.i(TAG, "");
                        } else {
                            Log.e(TAG, "add tag error !");
                        }
                    }
                });
    }

    public void addUserTags(String projectId, Map<String, List<String>> userTags) {
        db.collection("Project").document(projectId)
                .update("userTags",userTags)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Log.i(TAG, "");
                        } else {
                            Log.e(TAG, "add user tags error !");
                        }
                    }
                });
    }

    public List<String> getProjectList() {
        List<String> allProjectList = new ArrayList<>();
        db.collection("Project").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                allProjectList.add(documentSnapshot.getId());
                            }
                        } else {
                            Log.i(TAG, "Error getting projectList");
                        }
                    }
                });
        return allProjectList;
    }


    public List<String> getProjectMember(String projectId) {
        List<String> projectMember = new ArrayList<>();
        db.collection("Project").document(projectId)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    List<String> temp = task.getResult().toObject(Project.class).getMembers();
                    projectMember.addAll(temp);
                    Log.i(TAG+" getProjectMember::성공!",projectMember.toString());
                } else {
                    Log.i(TAG+" getProjectMember::ㄴㄴ!",projectMember.toString());
                }
            }
        });
        return projectMember;
    }


    public Map<String,List<String>> getUserTags(String projectId) {
        Map<String,List<String>> userTags = new HashMap<>();
        db.collection("Project").document(projectId)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    userTags.putAll(task.getResult().toObject(Project.class).getUserTags());
                    Log.i(TAG+" getProjectMember::성공!",userTags.toString());
                } else {
                    Log.i(TAG+" getProjectMember::ㄴㄴ!",userTags.toString());
                }
            }
        });
        return userTags;
    }

    public void updateDate(String projectId) {
        db.collection("Project").document(projectId)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Project project = documentSnapshot.toObject(Project.class);
                long days = project.getEndDate().getTime() - project.getStartDate().getTime();
                int diffDays = (int)(days / (1000 * 60 * 60 * 24)) + 1;
                List<String> plans = project.getPlans();
                List<String> temp;

                if (diffDays > plans.size()) {
                    temp = new ArrayList<>(plans);
                    int diff = diffDays - plans.size();
                    for (int i = 0; i < diff; i++) {
                        db.collection("Plan").add(new Plan())
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        temp.add(documentReference.getId());
                                        db.collection("Project").document(projectId).update("plans", temp).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                updatePlan(projectId);
                                            }
                                        });
                                    }
                                });
                    }
                } else {
                    temp = plans.subList(0, diffDays);
                    db.collection("Project").document(projectId).update("plans", temp).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            updatePlan(projectId);
                        }
                    });

                    List<String> deletePlan = plans.subList(diffDays, plans.size());

                    for (String planId : deletePlan) {
                        db.collection("Plan").document(planId).delete();
                    }
                }
            }
        });
    }

    public void updatePlan(String projectId) {
        db.collection("Project").document(projectId)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Project project = documentSnapshot.toObject(Project.class);
                List<String> plans = project.getPlans();

                Date startDate = project.getStartDate();
                long oneDay = 1000 * 60 * 60 * 24;

                for (int i = 0; i < plans.size(); i++) {
                    String planId = plans.get(i);
                    int finalI = i;
                    db.collection("Plan").document(planId).get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    Plan plan = documentSnapshot.toObject(Plan.class);
                                    plan.setProjectId(projectId);
                                    plan.setPlanDate(new Date(startDate.getTime() + (oneDay * finalI)));

                                    db.collection("Plan").document(planId).set(plan);
                                }
                            });
                }
            }
        });
    }

    public void updatePlanPinList(String projectId, int day, Pin pin) {
        db.collection("Project").document(projectId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                 String planId = documentSnapshot.toObject(Project.class).getPlans().get(day);

                 db.collection("Plan").document(planId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                     @Override
                     public void onSuccess(DocumentSnapshot documentSnapshot) {
                         List<Pin> pins = documentSnapshot.toObject(Plan.class).getPins();
                         pins.add(pin);

                         db.collection("Plan").document(planId).update("pins", pins);
                     }
                 });
            }
        });
    }
}   //end class





//이하 안쓰는 코드 빼놨습니다.





//    public MutableLiveData<Map<String, Project>> getDatepicker(String projectId) {
//        DocumentReference docRef = db.collection("Project").document(projectId);
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
//                        Map<String, Project> test = new HashMap<>();
//                        test.put(projectId, document.toObject(Project.class));
//                        currentProject.setValue(test);
//                    } else {
//                        Log.d(TAG, "No such document");
//                    }
//                } else {
//                    Log.d(TAG, "get failed with ", task.getException());
//                }
//            }
//        });
//        return currentProject;
//    }



//    public void datepickerUpdate(){
//        db.collection("Project").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                if (error != null) {
//                    Log.w("TAG", "addShapshotListener failed", error);
//                    return;
//                }
//
//                if(value != null) {
//                    Log.w(TAG, "여기가 되나안되나 함 봅시다 : "+ value.getDocuments());
//                    value.toObjects(Project.class);
////                    liveProject.setValue(document.add);
//                }
//            }
//        });
//    }