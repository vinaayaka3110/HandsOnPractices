SET SERVEROUTPUT ON;

DECLARE
    CURSOR cust_cursor IS
        SELECT c.CustomerID,
               l.LoanID
        FROM Customers c
        JOIN Loans l
        ON c.CustomerID = l.CustomerID
        WHERE c.Age > 60;

    v_customerid Customers.CustomerID%TYPE;
    v_loanid Loans.LoanID%TYPE;

BEGIN
    OPEN cust_cursor;

    LOOP
        FETCH cust_cursor INTO v_customerid, v_loanid;
        EXIT WHEN cust_cursor%NOTFOUND;

        UPDATE Loans
        SET InterestRate = InterestRate - 1
        WHERE LoanID = v_loanid;

        DBMS_OUTPUT.PUT_LINE(
            '1% discount applied to Loan ID: '
            || v_loanid
            || ' (Customer ID: '
            || v_customerid
            || ')'
        );

    END LOOP;

    CLOSE cust_cursor;

    COMMIT;
END;
/