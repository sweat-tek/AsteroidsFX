module dk.sdu.mmmi.cbse.scoring {
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.web;
    requires spring.context;
    requires spring.beans;
    requires spring.core;
    requires spring.data.jpa;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    // Removed H2 requirement since Spring Boot handles it
    requires java.sql;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    
    // Require the Common module for shared data models
    requires Common;
    // Require the Core module
    requires Core;
    
    // Export all necessary packages
    exports dk.sdu.mmmi.cbse.scoring;
    exports dk.sdu.mmmi.cbse.scoring.model;
    exports dk.sdu.mmmi.cbse.scoring.controller;
    exports dk.sdu.mmmi.cbse.scoring.service;
    exports dk.sdu.mmmi.cbse.scoring.repository;
    
    // Configure runtime access for Spring and Hibernate
    opens dk.sdu.mmmi.cbse.scoring to spring.core, spring.beans, spring.context;
    opens dk.sdu.mmmi.cbse.scoring.model to spring.core, org.hibernate.orm.core;
    opens dk.sdu.mmmi.cbse.scoring.controller to spring.core, spring.web;
    opens dk.sdu.mmmi.cbse.scoring.service to spring.core, spring.beans;
    opens dk.sdu.mmmi.cbse.scoring.repository to spring.core, spring.beans, spring.data.jpa;
}
