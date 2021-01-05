package com.together.nosheng.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.together.nosheng.model.pin.Pin;
import com.together.nosheng.model.project.Budget;
import com.together.nosheng.model.project.CheckList;
import com.together.nosheng.model.project.Post;
import com.together.nosheng.model.project.Project;
import com.together.nosheng.model.user.User;
import com.together.nosheng.repository.ProjectRepository;

import java.util.List;
import java.util.Map;

public class ProjectViewModel extends ViewModel {

    private String TAG = "ProjectViewModel";

    private LiveData<Map<String, Project>> currentProject;
    private LiveData<Map<String, Project>> userProjects;
    private ProjectRepository projectRepository;

    public ProjectViewModel() {
        projectRepository = new ProjectRepository();
    }

    public LiveData<Map<String, Project>> getCurrentProject() {
        return currentProject;
    }

    public void setCurrentProject(String projectId) {
        currentProject = projectRepository.getCurrentProject(projectId);
    }

    public void addUserProject(Project userProject) {
        projectRepository.addUserProject(userProject);
    }

    public void updateUserProject(Project userProject, String projectId) {
        projectRepository.updateUserProject(userProject, projectId);
    }

    public void setUserProjects() {
        userProjects = projectRepository.getUserProject();
    }

    public LiveData<Map<String, Project>> getUserProjects() {
        return userProjects;
    }

    public void addPost(String projectId, List<Post> posts) {
        projectRepository.addPost(projectId, posts);
    }

    public void addMember(String projectId, List<String> members) {
        projectRepository.addMember(projectId, members);
    }

    public void addTag(String projectId, List<String> tags) {
        projectRepository.addTag(projectId, tags);
    }

    public void addUserTags(String projectId, Map<String, List<String>> userTags) {
        projectRepository.addUserTags(projectId, userTags);
    }

    public void updateDate(String projectId) {
        projectRepository.updateDate(projectId);
    }

    public void updatePlanPinList(String projectId, int day, Pin pin) {
        projectRepository.updatePlanPinList(projectId, day, pin);
    }

    public void updateBudgets(String projectId, Map<String, Budget> budgets) {
        projectRepository.updateBudgets(projectId, budgets);
    }

    public void updateCheckList(String projectId, CheckList checkLists) {
        projectRepository.updateCheckList(projectId, checkLists);
    }

    public void searchProject(Context context, String joinCode) {
        projectRepository.searchProject(context, joinCode);
    }

    public void deleteMember(String projectId) {
        projectRepository.deleteMember(projectId);
    }
}
