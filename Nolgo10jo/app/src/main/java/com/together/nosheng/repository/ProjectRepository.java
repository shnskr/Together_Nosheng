package com.together.nosheng.repository;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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
import com.together.nosheng.model.project.Budget;
import com.together.nosheng.model.project.CheckList;
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

                        updateProjectList(documentReference.getId());
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
        MutableLiveData<Map<String, Project>> userProject = new MutableLiveData<>();
        Map<String, Project> userProjectMap = new HashMap<>();

        db.collection("User").document(GlobalApplication.firebaseUser.getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w(TAG, "addShapshotListener failed", error);
                    return;
                }
                if (value != null) {
                    List<String> projectList = value.toObject(User.class).getProjectList();
                    userProjectMap.clear();
                    userProject.setValue(userProjectMap);

                    for (String projectId : projectList) {
                        db.collection("Project").document(projectId).get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        DocumentSnapshot document = task.getResult();
                                        if (task.isSuccessful()) {
                                            if (document.exists()) {
                                                Log.i(TAG, projectId);
                                                userProjectMap.put(document.getId(), document.toObject(Project.class));
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

    public void updateProjectList(String projectId) {
        DocumentReference doc = db.collection("User").document(GlobalApplication.firebaseUser.getUid());
        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                User user = task.getResult().toObject(User.class);
                if (task.isSuccessful() && task.getResult().exists()) {
                    List<String> projects = user.getProjectList();
                    projects.add(projectId);

                    doc.update("projectList", projects);
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

    public void updateBudgets(String projectId, Map<String, Budget> budgets) {
        db.collection("Project").document(projectId).update("budgets", budgets);
    }

    public void updateCheckList(String projectId, CheckList checkLists) {
        db.collection("Project").document(projectId).update("checkLists."+GlobalApplication.firebaseUser.getUid(), checkLists);
//        db.collection("Project").document(projectId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                Map<String, Map<String, Boolean>> temp = (Map<String, Map<String, Boolean>>) documentSnapshot.get("checkLists");
//                temp.put(GlobalApplication.firebaseUser.getUid(), checkLists);
//
//                db.collection("Project").document(projectId).update("checkLists", temp);
//            }
//        });
    }

    public void searchProject(Context context, String joinCode) {
        db.collection("Project").document(joinCode).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                if (task.isSuccessful() && documentSnapshot.exists()) {
                    String uid = GlobalApplication.firebaseUser.getUid();
                    Project project = documentSnapshot.toObject(Project.class);

                    List<String> members = project.getMembers();

                    if (!members.contains(uid)) {
                        members.add(uid);

                        Map<String, CheckList> checkList = project.getCheckLists();
                        checkList.put(uid, new CheckList());

                        Map<String, List<String>> userTags = project.getUserTags();
                        userTags.put(uid, new ArrayList<>());

                        db.collection("Project").document(joinCode).set(project, SetOptions.merge());

                        db.collection("User").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                User user = documentSnapshot.toObject(User.class);
                                List<String> projectList = user.getProjectList();

                                if (projectList.contains(joinCode)) {
                                    Toast.makeText(context, "이미 가지고 있는 여행기입니다.", Toast.LENGTH_SHORT).show();
                                } else {
                                    projectList.add(joinCode);

                                    db.collection("User").document(uid).set(user, SetOptions.merge());

                                    Toast.makeText(context, "추가 완료", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                } else {
                    Toast.makeText(context, "존재 하지 않는 여행기 입니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void deleteMember(String projectId) {
        DocumentReference documentReference = db.collection("Project").document(projectId);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot doc = task.getResult();
                if (task.isSuccessful() && doc.exists()) {
                    Project project = doc.toObject(Project.class);
                    Map<String, CheckList> checkLists = project.getCheckLists();
                    String uid = GlobalApplication.firebaseUser.getUid();
                    checkLists.remove(uid);

                    if (checkLists.size() > 0) {
                        List<String> members = project.getMembers();
                        members.remove(uid);

                        Map<String, List<String>> userTags = project.getUserTags();
                        userTags.remove(uid);

                        documentReference.update("checkLists", checkLists);
                        documentReference.update("members", members);
                        documentReference.update("userTags", userTags);
                    } else {
                        List<String> plans = project.getPlans();

                        for (String planId : plans) {
                            db.collection("Plan").document(planId).delete();
                        }

                        documentReference.delete();
                    }
                }
            }
        });
    }
}

