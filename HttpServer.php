<?php
/**
 * Created by PhpStorm.
 * User: Wala
 * Date: 4/12/2019
 * Time: 1:40 PM
 */
$db = new mysqli("localhost","root","134687952a","firstDB");


    if(isset($_REQUEST['accountNumber']) && isset($_REQUEST['Password'])){
        $acctNum = $_REQUEST['accountNumber'];
        $pass = $_REQUEST['Password'];
        $loginQuery = "Select * from users where AccountNumber =" .$acctNum." and Password =".$pass;
        $result = $db->query($loginQuery);
        if($result->num_rows==0){
            echo"Failed";
        }
        else {
            echo "Success";
        }
    }
    else if (isset($_REQUEST['Operation']) && isset($_REQUEST['Ammount']) && isset($_REQUEST['accountNumber'])){
        $operation = $_REQUEST['Operation'];
        $Ammount = $_REQUEST['Ammount'];
        $acctNum = $_REQUEST['accountNumber'];
        $depositQuery = "Update users set Balance = ((Select Balance from users where AccountNumber =".$acctNum." ) + ".$Ammount." ) where AccountNumber =".$acctNum;;
        $WithdrawQuery = "Update users set Balance = ((Select Balance from users where AccountNumber =".$acctNum." ) - ".$Ammount." ) where AccountNumber =".$acctNum;;
        $getBalance = "Select Balance from users where AccountNumber =".$acctNum;
        //$ddd = "Update users set Balance =".$Ammount." where AccountNumber =".$acctNum;
        if($operation == "Withdraw"){
            $result = $db->query($getBalance);
            $row = $result->fetch_row();
            if($row[0] > $Ammount){
                $ddd = "Update users set Balance =".($row[0]-$Ammount)." where AccountNumber =".$acctNum;
                $result = $db->query($ddd);
                if($result){
                    echo "$Ammount Subtracted from Balance";
                }
                else {
                    echo "$Ammount Subtracted from Balancewww";
                }

            }
            else {
                echo "ammpunt < balance ";
            }
        }
        else if ($operation == "Deposit"){
            $result = $db->query($getBalance);
            $row = $result->fetch_row();
            $ddd = "Update users set Balance =".($row[0]+$Ammount)." where AccountNumber =".$acctNum;
            $result = $db->query($ddd);
            if($result){
                echo "$Ammount Added to the Balance";
            }
            else {
                echo "$Ammount Added to the Balanceeee";
            }

        }

    }
    else if(isset($_REQUEST['NewPass']) &&isset($_REQUEST['accountNumber']) ){
        $acctNum = $_REQUEST['accountNumber'];
        $newPassword = $_REQUEST['NewPass'];
        $changePassQuery = "Update users set Password =".$newPassword." where AccountNumber =".$acctNum;
        $db->query($changePassQuery);
        echo "Password Changed";
    }
    else if (isset($_REQUEST['accountNumber'])){
        $acctNum = $_REQUEST['accountNumber'];
        $getBalance = "Select Balance from users where AccountNumber =".$acctNum;
        $result = $db->query($getBalance);
        if($result){
            $row = $result->fetch_row();
            echo "$row[0]";
        }
    }
	
	
	
	//com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near 'and Password =' at line 1
