package com.example.ensamarketplace.utils;

import com.example.ensamarketplace.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Branch implements Serializable {

    private String name;
    private int image;

    public Branch(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public Branch(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public static List<Branch> getAllBranches(){
        List<Branch> branches = new ArrayList<>();

        Branch GI = new Branch("Genie informatique",R.drawable.ic_baseline_laptop_mac_24);
        Branch GIL = new Branch("Genie industriel",R.drawable.ic_baseline_factory_24);
        Branch GRT = new Branch("Genie réseaux et télécom",R.drawable.ic_baseline_wifi_tethering_24);
        Branch GE = new Branch("Genie électrique",R.drawable.ic_baseline_electric_bolt_24);
        Branch CP = new Branch("Cycle préparatoire",R.drawable.ic_baseline_school_24);
        Branch ALL = new Branch("ENSA",R.drawable.ic_baseline_business_24);


        branches.addAll(List.of(CP,GI,GIL,GRT,GE,ALL));
        System.out.println(branches);

        return branches;
    }
}
