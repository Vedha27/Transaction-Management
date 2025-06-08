package com.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.Scanner;

public class ABCBank {

	public static void main(String[] args) {

		String url="jdbc:mysql://localhost:3306/abc_bank";
		String un="root";
		String pwd="Vedha@276";

		Connection con=null;
		ResultSet res=null;
		PreparedStatement prepStmt1=null;
		PreparedStatement prepStmt2=null;
		PreparedStatement prepStmt3=null;
		PreparedStatement prepStmt4=null;
		PreparedStatement prepStmt5=null;
		Scanner scan=null; 
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver loaded Successfully...");

			con=DriverManager.getConnection(url,un,pwd);
			System.out.println("Connection Established.....");
			scan=new Scanner(System.in);

			//Login Module
			System.out.println("-------Welcome to ABC BANK---------");
			System.out.print("Enter you Account Number: ");
			int accNo=scan.nextInt();
			System.out.print("Enter Pin: ");
			int pin=scan.nextInt();

			prepStmt1=con.prepareStatement("Select * from account where acc_num=? and pin_num=?");

			prepStmt1.setInt(1,accNo);
			prepStmt1.setInt(2,pin);

			res=prepStmt1.executeQuery();

			res.next();		
			String name=res.getString(2);
			int bal=res.getInt(4);
			System.out.println("Welcome "+name);
			System.out.println("Available Balance is: "+bal);

			//Transfer Module
			System.out.println("---------Transfer Details----------");
			System.out.print("Enter the  beneficiary account number: ");
			int recAccNo=scan.nextInt();
			System.out.print("Enter transfer amount: ");
			int tAmount=scan.nextInt();

			con.setAutoCommit(false);
			
			Savepoint s=con.setSavepoint();

			prepStmt2=con.prepareStatement("update account set balance=balance-? where acc_num=?");
			prepStmt2.setInt(1, tAmount);
			prepStmt2.setInt(2,accNo);
			prepStmt2.executeUpdate();

			System.out.println("--------Incoming credit request------");
			System.out.println(name+" wants to transfer "+tAmount);
			System.out.println("Press Y to receive");
			System.out.println("Press N to reject");
			String choice=scan.next();

			if(choice.equalsIgnoreCase("Y"))
			{
				prepStmt3=con.prepareStatement("update account set balance=balance+? where acc_num=?");
				prepStmt3.setInt(1, tAmount);
				prepStmt3.setInt(2,recAccNo);
				prepStmt3.executeUpdate();

				prepStmt4=con.prepareStatement("select * from account where acc_num=?");
				prepStmt4.setInt(1,recAccNo);
				ResultSet res2=prepStmt4.executeQuery();
				res2.next();

				System.out.println("Updated balance is: "+res2.getInt(4));
			}
			else
			{
				con.rollback(s);
				prepStmt5=con.prepareStatement("select * from account where acc_num=?");
				prepStmt5.setInt(1, recAccNo);
				ResultSet res2=prepStmt5.executeQuery();
				res2.next();

				System.out.println("Existing balance: "+res2.getInt(4));
			}
			con.commit();
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
}
