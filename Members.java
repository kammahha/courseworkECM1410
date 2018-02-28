package library;

public class Members
{
    //Include private here, good practice!
    private int id;
    private String fName, lName, dateJoin;

    /**
     * Members Constructor: Takes the given parameters (id, fName, lName, dateJoin) to create a memberlist list
     * (Array List to be exact).
     * @param id ID of given member in text file
     * @param fName First name of given member ID
     * @param lName Last name of given member ID
     * @param dateJoin Date that the given member joined
     */
    public Members(int id, String fName, String lName, String dateJoin)
    {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.dateJoin = dateJoin;
    }

    public int getId() {
        return id;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getDateJoin() {
        return dateJoin;
    }
}
