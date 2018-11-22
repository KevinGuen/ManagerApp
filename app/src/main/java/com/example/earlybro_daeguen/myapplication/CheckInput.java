package com.example.earlybro_daeguen.myapplication;

/**
 * Created by Earlybro_DaeGuen on 2017-11-03.
 */

public class CheckInput {

    public boolean CheckNull(String str){
        if(str.length() <=0)
            return false;

        else return true;
    }


    public boolean CheckID(String shopID) {
                int checkNum = 0;
        for (int i = 0; i < shopID.length(); i++) {


            if (shopID.charAt(i) != 'a' && shopID.charAt(i) != 'b' && shopID.charAt(i) != 'c' && shopID.charAt(i) != 'd' && shopID.charAt(i) != 'e' && shopID.charAt(i) != 'f'
                    && shopID.charAt(i) != 'g' && shopID.charAt(i) != 'h' && shopID.charAt(i) != 'i' && shopID.charAt(i) != 'j' && shopID.charAt(i) != 'k' && shopID.charAt(i) != 'l'
                    && shopID.charAt(i) != 'm' && shopID.charAt(i) != 'n' && shopID.charAt(i) != 'o' && shopID.charAt(i) != 'p' && shopID.charAt(i) != 'q' && shopID.charAt(i) != 'r'
                    && shopID.charAt(i) != 's' && shopID.charAt(i) != 't' && shopID.charAt(i) != 'u' && shopID.charAt(i) != 'v' && shopID.charAt(i) != 'w' && shopID.charAt(i) != 'x'
                    && shopID.charAt(i) != 'y' && shopID.charAt(i) != 'z' && shopID.charAt(i) != 'A' && shopID.charAt(i) != 'B' && shopID.charAt(i) != 'C' && shopID.charAt(i) != 'D'
                    && shopID.charAt(i) != 'E' && shopID.charAt(i) != 'F' && shopID.charAt(i) != 'G' && shopID.charAt(i) != 'H' && shopID.charAt(i) != 'I' && shopID.charAt(i) != 'J'
                    && shopID.charAt(i) != 'K' && shopID.charAt(i) != 'L' && shopID.charAt(i) != 'M' && shopID.charAt(i) != 'N' && shopID.charAt(i) != 'O' && shopID.charAt(i) != 'P'
                    && shopID.charAt(i) != 'Q' && shopID.charAt(i) != 'R' && shopID.charAt(i) != 'S' && shopID.charAt(i) != 'T' && shopID.charAt(i) != 'U' && shopID.charAt(i) != 'V'
                    && shopID.charAt(i) != 'W' && shopID.charAt(i) != 'X' && shopID.charAt(i) != 'Y' && shopID.charAt(i) != 'Z' && shopID.charAt(i) != '1' && shopID.charAt(i) != '2'
                    && shopID.charAt(i) != '3' && shopID.charAt(i) != '4' && shopID.charAt(i) != '5' && shopID.charAt(i) != '6' && shopID.charAt(i) != '7' && shopID.charAt(i) != '8'
                    && shopID.charAt(i) != '9' && shopID.charAt(i) != '0') {
                    checkNum =1;
            }
        }

        if(checkNum == 1)
            return  false;

        else return true;
    }

    public boolean CheckEmail(String shopEmail){
        int checck1 = 0;
        int checck2 = 0;

        for(int i=0; i<shopEmail.length(); i++){
            if(shopEmail.charAt(i) == '@')
                checck1 ++;
            if(shopEmail.charAt(i) == '.')
                checck2 ++;
        }

        if(checck1 == 1 && checck2 ==1)
            return true;
        else return false;
    }

    public boolean CheckshopTel(String shopTel){
        int checkNum=0;
        for(int i=0; i<shopTel.length(); i++){
            if(shopTel.charAt(i) != '1' && shopTel.charAt(i)!='2' && shopTel.charAt(i)!='3'
                    && shopTel.charAt(i)!='4' && shopTel.charAt(i)!='5' && shopTel.charAt(i)!='6'
                    && shopTel.charAt(i)!='7' && shopTel.charAt(i)!='8' && shopTel.charAt(i)!='9'
                    && shopTel.charAt(i)!='0')
                checkNum = 1;
        }
        if(checkNum == 0)
            return true;

        else return  false;
    }

    public boolean CheckmanagerTel(String managerTel){
        int checkNum=0;
        for(int i=0; i<managerTel.length(); i++){
            if(managerTel.charAt(i) != '1' && managerTel.charAt(i)!='2' && managerTel.charAt(i)!='3'
                    && managerTel.charAt(i)!='4' && managerTel.charAt(i)!='5' && managerTel.charAt(i)!='6'
                    && managerTel.charAt(i)!='7' && managerTel.charAt(i)!='8' && managerTel.charAt(i)!='9'
                    && managerTel.charAt(i)!='0')
                checkNum = 1;
        }
        if(checkNum == 0)
            return true;

        else return  false;
    }


}
