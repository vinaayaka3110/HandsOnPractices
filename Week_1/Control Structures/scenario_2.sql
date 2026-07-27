SET SERVEROUTPUT ON;

DECLARE
    CURSOR vip_cursor IS
        SELECT CustomerID
        FROM Customers
        WHERE Balance > 10000;

    v_customerid Customers.CustomerID%TYPE;

BEGIN
    OPEN vip_cursor;

    LOOP
        FETCH vip_cursor INTO v_customerid;
        EXIT WHEN vip_cursor%NOTFOUND;

        UPDATE Customers
        SET IsVIP = 'TRUE'
        WHERE CustomerID = v_customerid;

        DBMS_OUTPUT.PUT_LINE(
            'Customer '
            || v_customerid
            || ' promoted to VIP.'
        );

    END LOOP;

    CLOSE vip_cursor;

    COMMIT;
END;
/