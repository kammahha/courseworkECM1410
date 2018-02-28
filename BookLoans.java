package library;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static java.time.temporal.ChronoUnit.DAYS;

public class BookLoans
{
    private LocalDate borrowDate;
    //Include private here, good practice!
    private int id, bookID, memberID;

    /**
     * BookLoans Constructor: Takes the given parameters (id, bookID, memberID, borrowDate) to create bookloan list
     * (Array List to be exact).
     * @param id ID of the Loan ID
     * @param bookID ID of loaned bookID
     * @param memberID ID of the member who loaned bookID
     * @param borrowDate Date in which the loan occurred
     */

    public BookLoans(int id, int bookID, int memberID, LocalDate borrowDate)
    {
        this.id = id;
        this.bookID = bookID;
        this.memberID = memberID;
        this.borrowDate = borrowDate;
    }

    public BookLoans(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getBookID() { return bookID; }

    public int getMemberID() {
        return memberID;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public float fineCalc() {
        float day_difference =  DAYS.between(borrowDate, LocalDate.now());
        float fine_value = (float) 0.1;
        if (day_difference > 30) {
            return (day_difference * fine_value);
        }
        else{
            return 0;
        }
    }
}

