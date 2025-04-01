package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TelegramTest {
    @Test
    public void isValidHandle_validFiveCharacters_success() {
        String telegramHandle = "@abcde";
        assertTrue(Telegram.isValidHandle(telegramHandle));
    }

    @Test
    public void isValidHandle_validLargestCharacters_success() {
        String telegramHandle = "@a2345678901234567890123456789012";
        assertTrue(Telegram.isValidHandle(telegramHandle));
    }

    @Test
    public void isValidHandle_invalidHandleTooShort_success() {
        String telegramHandle = "@abcd";
        assertFalse(Telegram.isValidHandle(telegramHandle));
    }

    @Test
    public void isValidHandle_invalidHandleTooLong_success() {
        String telegramHandle = "@a23456789012345678901234567890123";
        assertFalse(Telegram.isValidHandle(telegramHandle));
    }

    @Test
    public void isValidHandle_invalidLeadingCharacter_returnFalse() {
        String leadingUnderscore = "@_abcdef";
        String leadingNumber = "@1abcdef";
        assertFalse(Telegram.isValidHandle(leadingUnderscore));
        assertFalse(Telegram.isValidHandle(leadingNumber));
    }

    @Test
    public void isValidHandle_invalidTrailingCharacter_returnFalse() {
        String trailingUnderscore = "@abcdef_";
        assertFalse(Telegram.isValidHandle(trailingUnderscore));
    }
}
