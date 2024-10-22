package views;

import dao.userDAO;
import model.User;
import services.GenerateOTP;
import services.SendOTPService;
import services.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;

public class welcome {


    public void welcomeScreen(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Welcome to the App");
        System.out.println("Press 1 for Login");
        System.out.println("Press 2 for SignUp");
        System.out.println("Press 0 for Exit");
        int choice = 0;
        try{
            choice = Integer.parseInt(br.readLine());
        }catch (IOException ex){
            ex.printStackTrace();
        }

        switch (choice){

            case 1 -> login();
            case 2 -> signUp();
            case 0 -> System.exit(0);
        }
    }

    private void login() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Email");
        String email = sc.nextLine();
        try{
            if(userDAO.isExists(email)){
                String genOTP = GenerateOTP.getOtp();
                SendOTPService.sendOTP(email,genOTP);
                System.out.println("Enter the OTP");
                String otp = sc.nextLine();
                if(otp.equals(genOTP)){
                   new UserView(email).home();
                }else{
                    System.out.println("Wrong OTP");
                }
            } else{
                System.out.println("User Not Found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void signUp() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Name");
        String name = sc.nextLine();
        System.out.println("Enter Email");
        String email = sc.nextLine();

        String genOTP = GenerateOTP.getOtp();
        SendOTPService.sendOTP(email,genOTP);
        System.out.println("Enter the OTP");
        String otp = sc.nextLine();
        if(otp.equals(genOTP)){
            User user = new User(name,email);
            int response = UserService.saveUser(user);
            switch (response){
                case 0 -> System.out.println("User Registered");
                case 1 -> System.out.println("Signup Successfully");
            }
        }else{
            System.out.println("Wrong OTP");
        }
    }
}
