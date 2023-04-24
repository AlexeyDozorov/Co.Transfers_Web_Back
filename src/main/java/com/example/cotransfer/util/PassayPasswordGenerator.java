package com.example.cotransfer.util;


import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.stereotype.Component;

@Component
public class PassayPasswordGenerator {
    public static String ERROR_CODE = "Password error";
    public static int NUMB_OF_LOWER_CASE = 2;
//    public static int NUMB_OF_UPPER_CASE = 2;
    public static int NUMB_OF_LOWER_DIGIT = 2;
//    public static int NUMB_OF_SPECIAL = 2;
    public static int TOTAL_NUMB = 8;

    public String generatePassayPassword() {
        PasswordGenerator gen = new PasswordGenerator();
        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(NUMB_OF_LOWER_CASE);

//        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
//        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
//        upperCaseRule.setNumberOfCharacters(NUMB_OF_UPPER_CASE);

        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(NUMB_OF_LOWER_DIGIT);

//        CharacterData specialChars = new CharacterData() {
//            public String getErrorCode() {
//                return ERROR_CODE;
//            }
//
//            public String getCharacters() {
//                return "!@#$%^&*()_+";
//            }
//        };
//        CharacterRule splCharRule = new CharacterRule(specialChars);
//        splCharRule.setNumberOfCharacters(NUMB_OF_SPECIAL);

//        return gen.generatePassword(TOTAL_NUMB, splCharRule, lowerCaseRule,
//                upperCaseRule, digitRule);

        return gen.generatePassword(TOTAL_NUMB, lowerCaseRule, digitRule);
    }
}
