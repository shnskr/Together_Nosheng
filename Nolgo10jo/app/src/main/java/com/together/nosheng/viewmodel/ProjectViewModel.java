package com.together.nosheng.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.together.nosheng.model.project.Project;
import com.together.nosheng.repository.ProjectRepository;

import java.util.List;
import java.util.Map;

public class ProjectViewModel extends ViewModel {

    private MutableLiveData<Map<String, Project>> currentProject;
    private LiveData<List<Project>> userProjects;
    private ProjectRepository projectRepository;
    private String projectId;

    public ProjectViewModel() {
        projectId = "C7kEu5V2XV3BZ4e8ztvc";
        projectRepository = new ProjectRepository();
        currentProject = projectRepository.getDatepicker(projectId);
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
}
