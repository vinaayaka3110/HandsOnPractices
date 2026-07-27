SET SERVEROUTPUT ON;

CREATE OR REPLACE PROCEDURE ProcessMonthlyInterest
AS
BEGIN

    UPDATE Accounts
    SET Balance = Balance + (Balance * 0.01)
    WHERE AccountType='Savings';

    DBMS_OUTPUT.PUT_LINE('Monthly interest applied successfully.');

    COMMIT;

END;
/


BEGIN
    ProcessMonthlyInterest;
END;
/