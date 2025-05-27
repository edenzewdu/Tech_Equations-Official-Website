package com.lucy.controller;

import com.lucy.model.TeamMember;
import com.lucy.model.User;
import com.lucy.repository.TeamMemberRepository;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.RowEditEvent;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Named
@SessionScoped
public class MemberController implements Serializable {

    private final TeamMemberRepository teamMemberRepository;

    private List<TeamMember> members;
    private TeamMember selectedMember;
    private TeamMember tempEditedMember;
    private String searchTerm;
    private TeamMember member = new TeamMember();

    private String name;
    private String role;
    private String image_url;

    private boolean showCreateForm = false;

    @Inject
    public MemberController(TeamMemberRepository teamMemberRepository) {
        this.teamMemberRepository = teamMemberRepository;
    }

    @PostConstruct
    public void init() {
        loadMembers();
    }

    public void loadMembers() {
        this.members = teamMemberRepository.getAllTeamMembers();
    }
    public List<TeamMember> getMembers() {
        return members;
    }

    public List<TeamMember> searchMembers() {

        members = teamMemberRepository.searchTeamMembers(searchTerm);
        return members;
    }
    public TeamMember findTeamMemberById(String id) {
        return teamMemberRepository.getTeamMemberById(id);
    }


    public String createMember() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            TeamMember newMember = new TeamMember();
            newMember.setName(name);
            newMember.setRole(role);
            newMember.setImageUrl(image_url);
            newMember.setCreatedAt(LocalDateTime.now());
            newMember.setUpdatedAt(LocalDateTime.now());

            boolean created = teamMemberRepository.saveTeamMember(newMember);

            if (created) {
                context.addMessage(null, new FacesMessage("Team member created successfully"));
                loadMembers();
                showCreateForm = false;
                return "member-list?faces-redirect=true";
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Member may already exist", null));
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unexpected error", null));
            return null;
        }
    }

    public void deleteMember() {
        try {
            if (selectedMember != null) {
                teamMemberRepository.deleteTeamMemberById(selectedMember.getId());
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Team member deleted successfully"));
                loadMembers();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No member selected", "Please select a member first"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Delete failed", "Could not delete member"));
        }
    }

    public void save() {
        if (member.getId() == null) {
            member.setCreatedAt(LocalDateTime.now());
        }
        member.setUpdatedAt(LocalDateTime.now());
        teamMemberRepository.saveTeamMember(member);
        clearForm();
        loadMembers();
    }

    public void onRowEdit(RowEditEvent<TeamMember> event) {
        tempEditedMember = event.getObject();
    }

    public void confirmEdit() {
        if (tempEditedMember != null) {
            tempEditedMember.setUpdatedAt(LocalDateTime.now());
            teamMemberRepository.updateTeamMember(tempEditedMember);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Member updated successfully"));
            loadMembers();
            tempEditedMember = null;
        }
    }

    public void cancelEdit() {
        this.members = teamMemberRepository.getAllTeamMembers();
        tempEditedMember = null;
    }

    public void clearForm() {
        this.member = new TeamMember();
        this.name = "";
        this.role = "";
        this.image_url = "";
    }

    public void prepareDelete(TeamMember member) {
        this.selectedMember = member;
    }

    // Getters and Setters

    public TeamMember getMember() {
        return member;
    }

    public void setMember(TeamMember member) {
        this.member = member;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImageUrl() {
        return image_url;
    }

    public void setImageUrl(String image_url) {
        this.image_url = image_url;
    }

    public boolean isShowCreateForm() {
        return showCreateForm;
    }

    public void setShowCreateForm(boolean showCreateForm) {
        this.showCreateForm = showCreateForm;
    }

    public String getSearchTerm() { return searchTerm; }
    public void setSearchTerm(String searchTerm) { this.searchTerm = searchTerm; }

}
