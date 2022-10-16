package org.skan.model;

import java.util.ArrayList;
import java.util.List;

public class WorkTeam {

    public String QA, PM, Mobile;
    public List<String> Frontend, Backend;
    public WorkTeam.Projects projects;


    public static class Projects{
        public String campain;
        public String banking;
    }
}
