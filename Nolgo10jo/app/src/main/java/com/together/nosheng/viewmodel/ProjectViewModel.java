package com.together.nosheng.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.together.nosheng.model.project.Project;

import java.util.List;

public class ProjectViewModel extends ViewModel {
    public LiveData<List<Project>> project;
}
