package com.together.nosheng.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.together.nosheng.model.project.Project;
import com.together.nosheng.repository.ProjectRepository;

import java.util.ArrayList;
import java.util.List;

public class ProjectViewModel extends ViewModel {
    public LiveData<ArrayList<Project>> projects;

    private ProjectRepository projectRepository;

    public ProjectViewModel() {
        projectRepository = new ProjectRepository();
        projects = projectRepository.getUserProjectList();
    }
}
