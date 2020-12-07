package com.together.nosheng.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.together.nosheng.model.project.Project;
import com.together.nosheng.repository.ProjectRepository;

import java.util.List;
import java.util.Map;

public class ProjectViewModel extends ViewModel {

    private MutableLiveData<Map<String, Project>> currentProject;
    private LiveData<Map<String, Project>> userProjects;
    private ProjectRepository projectRepository;

    public ProjectViewModel() {
        projectRepository = new ProjectRepository();
    }


    public MutableLiveData<Map<String, Project>> projectLiveData(){
        return currentProject;
    }

    public void addUserProject(Project userProject){
        projectRepository.addUserProject(userProject);
    }

    public void updateUserProject(Project userProject, String projectId){
        projectRepository.updateUserProject(userProject, projectId);
    }

    public void setUserProjects() {
        userProjects = projectRepository.getUserProject();
    }

    public LiveData<Map<String, Project>> getUserProjects() {
        return userProjects;
    }
}
