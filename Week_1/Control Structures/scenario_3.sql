SET SERVEROUTPUT ON;

DECLARE
    CURSOR loan_cursor IS
        SELECT c.CustomerName,
               l.LoanID,
               l.DueDate
        FROM Customers c
        JOIN Loans l
        ON c.CustomerID = l.CustomerID
        WHERE l.DueDate BETWEEN SYSDATE
                            AND SYSDATE + 30;

    v_name Customers.CustomerName%TYPE;
    v_loanid Loans.LoanID%TYPE;
    v_duedate Loans.DueDate%TYPE;

BEGIN
    OPEN loan_cursor;

    LOOP
        FETCH loan_cursor
        INTO v_name,
             v_loanid,
             v_duedate;

        EXIT WHEN loan_cursor%NOTFOUND;

        DBMS_OUTPUT.PUT_LINE(
            'Reminder: Dear '
            || v_name
            || ', your Loan ID '
            || v_loanid
            || ' is due on '
            || TO_CHAR(v_duedate, 'DD-MON-YYYY')
        );

    END LOOP;

    CLOSE loan_cursor;
END;
/