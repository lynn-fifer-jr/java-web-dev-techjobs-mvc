package org.launchcode.javawebdevtechjobsmvc.controllers;

import org.launchcode.javawebdevtechjobsmvc.models.Job;
import org.launchcode.javawebdevtechjobsmvc.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static org.launchcode.javawebdevtechjobsmvc.controllers.ListController.columnChoices;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @RequestMapping(value = "")
    public String search(Model model) {
        model.addAttribute("columns", columnChoices);
        return "search";
    }

    @RequestMapping(value = "results", method = {RequestMethod.POST})
    public String displaySearchResults(Model model, @RequestParam String searchType, @RequestParam String searchTerm){
        ArrayList<Job> jobs;
        if (searchTerm.toLowerCase().equals("all") &&  searchType.equals("all")) {
            jobs = JobData.findAll();
            model.addAttribute("title", "All jobs ");
            model.addAttribute("employers", JobData.getAllEmployers());
            model.addAttribute("locations", JobData.getAllLocations());
            model.addAttribute("positions", JobData.getAllPositionTypes());
            model.addAttribute("skills", JobData.getAllCoreCompetency());
        }else if (searchTerm.isBlank() && searchType.equals("all")){
            jobs = JobData.findAll();
            model.addAttribute("title", "All jobs ");
            model.addAttribute("employers", JobData.getAllEmployers());
            model.addAttribute("locations", JobData.getAllLocations());
            model.addAttribute("positions", JobData.getAllPositionTypes());
            model.addAttribute("skills", JobData.getAllCoreCompetency());
        }else{
            jobs = JobData.findByColumnAndValue(searchType, searchTerm);
            if (searchTerm.isBlank()){
                searchTerm = "All";
            }
            model.addAttribute("title", "Jobs, by " + searchType + ": " + searchTerm);
            model.addAttribute("employers", JobData.getAllEmployers());
            model.addAttribute("locations", JobData.getAllLocations());
            model.addAttribute("positions", JobData.getAllPositionTypes());
            model.addAttribute("skills", JobData.getAllCoreCompetency());
        }
        model.addAttribute("jobs", jobs);
        return "list-jobs";

    }

    // TODO #3 - Create a handler to process a search request and render the updated search view.

}
