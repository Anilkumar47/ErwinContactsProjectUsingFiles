package com.erwin.serviceimpl;

import com.erwin.client.ContactMenu;
import static com.erwin.client.ContactMenu.main;
import static com.erwin.client.ContactMenu.integerValidate;
import static com.erwin.client.ContactMenu.longValidate;
import static com.erwin.client.ContactMenu.stringValidate;
import com.erwin.module.Contact;
import com.erwin.service.ContactOperations;
import com.ewin.Exception.ContactException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OperationsImplementation implements ContactOperations {

    public enum searchOption {
        SEARCH_BY_NAME, SEARCH_BY_EMAIL, SEARCH_BY_MOBILE, GO_BACK
    }

    public enum updateOption {
        UPDATE_BY_NAME, UPDATE_BY_EMAIL, UPDATE_BY_MOBILE, GO_BACK
    }

    List<Contact> allContacts = new CopyOnWriteArrayList<Contact>();
    Scanner s = new Scanner(System.in);

    @Override
    public void create() {
        Contact cp = new Contact();
        //inserting name
        // boolean istrue = true;
        while (true) {
            System.out.println("To create contact Enter name of person :");
            String name = s.next();
            String validateName = stringValidate(name);
            if (!validateName.equalsIgnoreCase("error")) {
                cp.setName(validateName);
                break;
            } else {
                System.out.println("Please enter a valid input!");
            }
        }
        //inserting email
        while (true) {
            System.out.println("Enter email :");
            String email = s.next();
            String validateEmail = stringValidate(email);
            if (!validateEmail.equalsIgnoreCase("error")) {
                cp.setEmail(validateEmail);
                break;
            } else {
                System.out.println("Please enter a valid input!");
            }
        }
        //inserting mobile number
        while (true) {
            System.out.println("Enter Mobile Number :");
            String mobile = s.next();
            int lengthOfMobileNumber = mobile.length();
            String validateMobile = longValidate(mobile);
            if (lengthOfMobileNumber != 10) {
                System.out.println("Mobile Number should be  10 digits");
                continue;
            }
            if (mobile.equalsIgnoreCase(validateMobile)) {
                cp.setMobile(validateMobile);
                break;
            }
        }
        System.out.println("Contact Created Successfully!");
        allContacts.add(cp);
        System.out.println("You Want to create more(Y/N)");
        char createMore = s.next().charAt(0);
        if (createMore == 'y' || createMore == 'Y') {
            create();
        }

    }

    @Override
    public void delete() {
        if (allContacts == null || allContacts.isEmpty()) {
            System.out.println("No Data Found to Delete !");
        } else {
            System.out.println("Enter the mobile number that you want to Delete!");
            String mobileToDelete = s.next();
            int lengthOfMobileNumber = mobileToDelete.length();
            String validateMobile = longValidate(mobileToDelete);
            allContacts.forEach((Contact i) -> {
                if (mobileToDelete.equals(i.getMobile())) {
                    System.out.println("Are you sure to delete(Y/N) " + mobileToDelete);
                    char reCheck = s.next().charAt(0);
                    if (reCheck == 'y' || reCheck == 'Y') {
                        allContacts.remove(i);
                    }
                } else {
                    System.out.println("Not data with this Mobile number");
                }
            });
        }
    }

    @Override
    public void search() {
        //System.out.println("1.Search by Name \n2.Search by Email \n3.Search by Mobile\n4.Go back");
        if (allContacts == null || allContacts.isEmpty()) {
            System.out.println("No Data Found!");
        } else {
            int k = 1;
            for (int i = 0; i < searchOption.values().length; i++) {
                System.out.println(k++ + "." + searchOption.values()[i]);
            }
            String inputValue = s.next();
            int search = integerValidate(inputValue);

            if (search > searchOption.values().length) {
                System.out.println("Please enter a valid Number");
            }
            try {
                searchOption menu = searchOption.values()[search - 1];
                switch (menu) {
                    case SEARCH_BY_NAME:
                        searchByName();
                        break;
                    case SEARCH_BY_EMAIL:
                        searchByEmail();
                        break;
                    case SEARCH_BY_MOBILE:
                        searchByMobile();
                        break;
                    case GO_BACK: {
                        try {
                            main(null);
                        } catch (ContactException ex) {
                            System.out.println("You cannot go back!");
                        }
                    }
                    break;

                }
            } catch (Exception e) {
                // System.out.println("please enter the valid number!");
                search();
            }
        }

    }

    private void searchByName() {
        while (true) {
            System.out.println("Enter the Name that you want to search : ");
            String name = s.next();
            String validateName = stringValidate(name);
            if (!validateName.equalsIgnoreCase("error")) {
                displayHeading();
                allContacts.stream()
                        .filter(p -> name.equalsIgnoreCase(p.getName()))
                        .forEach(p -> System.out.print(p.getName() + "\t\t" + p.getEmail() + "\t\t\t" + p.getMobile() + "\n"));
                break;
            } else {
                System.out.println("Please enter a valid input!");
            }
        }
    }

    private void searchByEmail() {
        while (true) {
            System.out.println("Enter the Name that you want to search : ");
            String email = s.next();
            String validateName = stringValidate(email);
            if (!validateName.equalsIgnoreCase("error")) {
                displayHeading();
                allContacts.stream()
                        .filter(p -> email.equalsIgnoreCase(p.getEmail()))
                        .forEach(p -> System.out.print(p.getName() + "\t\t" + p.getEmail() + "\t\t\t" + p.getMobile() + "\n"));
                break;
            } else {
                System.out.println("Please enter a valid input!");
            }
        }
    }

    private void searchByMobile() {
        System.out.println("Enter the Mobile Number that you want to search : ");
        String mobile = s.next();
        displayHeading();
        allContacts.stream()
                .filter(p -> mobile.equalsIgnoreCase(p.getMobile()))
                .forEach(p -> System.out.print(p.getName() + "\t\t" + p.getEmail() + "\t\t\t" + p.getMobile() + "\n"));

    }

    @Override
    public void update() {
        // System.out.println("1.Update by Name \n2.Update by Email \n3.Update by Mobile");
        if (allContacts == null || allContacts.isEmpty()) {
            System.out.println("No Data Found!");
        } else {
            int k = 1;
            for (int i = 0; i < updateOption.values().length; i++) {
                System.out.println(k++ + "." + updateOption.values()[i]);
            }
            String inputValue = s.next();
            int update = integerValidate(inputValue);
            if (update > updateOption.values().length) {
                System.out.println("Please enter a valid Number");
            }
            try {
                updateOption menu = updateOption.values()[update - 1];
                switch (menu) {
                    case UPDATE_BY_NAME:
                        updateByName();
                        break;
                    case UPDATE_BY_EMAIL:
                        updateByEmail();
                        break;
                    case UPDATE_BY_MOBILE:
                        updateByMobile();
                        break;
                    case GO_BACK: {
                        try {
                            ContactMenu.main(null);
                        } catch (ContactException ex) {
                            Logger.getLogger(OperationsImplementation.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;

                }
            } catch (Exception e) {
                update();
            }
        }

    }

    private void updateByName() {
        System.out.println("Enter the name that you want to update");
        String name = s.next();
        updateMenu(name, "name");

    }

    private void updateByEmail() {
        System.out.println("Enter the Email that you want to update");
        String email = s.next();
        updateMenu(email, "email");
    }

    private void updateByMobile() {
        System.out.println("Enter the Mobile that you want to update");
        String mobile = s.next();
        updateMenu(mobile, "mobile");

    }

    private void updateMenu(String value, String type) {
        System.out.println("Choose the option that you want you update with " + type + " :" + value);
        System.out.println("1.Name\n2.Email\n3.Mobile");
        String inputValue = s.next();
        int optionToUpdate = integerValidate(inputValue);
        if (optionToUpdate > 4) {
            System.out.println("Please enter Valid Number");
        }
        try {
            switch (optionToUpdate) {
                case 1:
                    updateName(value, type);
                    break;
                case 2:
                    updateEmail(value, type);
                    break;
                case 3:
                    updateMobile(value, type);
                    break;
                case 4: {
                    try {
                        main(null);
                    } catch (ContactException ex) {
                        System.out.println("You cannot go back!");
                    }
                }
                break;
            }
        } catch (Exception e) {
            update();
        }
    }

    private void updateName(String name, String type) {
        System.out.println("Enter New Name that you want update.");
        String NewName = s.next();
        if (type.equalsIgnoreCase("name")) {
            allContacts.stream().filter(i -> i.getName().equalsIgnoreCase(name)).forEach(i -> i.setName(NewName));
        } else if (type.equalsIgnoreCase("email")) {
            allContacts.stream().filter(i -> i.getEmail().equalsIgnoreCase(name)).forEach(i -> i.setName(NewName));
        } else {
            allContacts.stream().filter(i -> i.getMobile().equalsIgnoreCase(name)).forEach(i -> i.setName(NewName));
        }
        System.out.println("Your Name is Updated Successfully!");
        display();

    }

    private void updateEmail(String name, String type) {
        System.out.println("Enter New Email that you want update.");
        String NewEmail = s.next();
        if (type.equalsIgnoreCase("name")) {
            allContacts.stream().filter(i -> i.getName().equalsIgnoreCase(name)).forEach(i -> i.setEmail(NewEmail));
        } else if (type.equalsIgnoreCase("email")) {
            allContacts.stream().filter(i -> i.getEmail().equalsIgnoreCase(name)).forEach(i -> i.setEmail(NewEmail));
        } else {
            allContacts.stream().filter(i -> i.getMobile().equalsIgnoreCase(name)).forEach(i -> i.setEmail(NewEmail));
        }
        System.out.println("Your Email is Updated Successfully!");
        display();

    }

    private void updateMobile(String name, String type) {
        System.out.println("Enter New Mobile Number that you want update.");
        String NewMobile = s.next();
        if (type.equalsIgnoreCase("name")) {
            allContacts.stream().filter(i -> i.getName().equalsIgnoreCase(name)).forEach(i -> i.setMobile(NewMobile));
        } else if (type.equalsIgnoreCase("email")) {
            allContacts.stream().filter(i -> i.getEmail().equalsIgnoreCase(name)).forEach(i -> i.setMobile(NewMobile));
        } else {
            allContacts.stream().filter(i -> i.getMobile().equalsIgnoreCase(name)).forEach(i -> i.setMobile(NewMobile));
        }
        System.out.println("Your Mobile Number is Updated Successfully!");
        display();

    }

    private void displayHeading() {
        System.out.println("---------------------------------------------------------");
        System.out.println("Name \t\t Email \t\t\t Mobile");
        System.out.println("---------------------------------------------------------");
    }

    @Override
    public void display() {
        System.out.println("Here is the list of Contacts");
        displayHeading();
        if (allContacts == null || allContacts.isEmpty()) {
            System.out.println("No Data Found!");
        }
        allContacts.forEach(i -> {
            System.out.print(i.getName() + "\t\t" + i.getEmail() + "\t\t\t" + i.getMobile() + "\n");
        });
        System.out.println("---------------------------------------------------------");

    }

}
