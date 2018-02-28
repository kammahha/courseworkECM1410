package library;

public class Books
{
    //Include private here, good practice!
    private int id, year, numberCopies;
    private String title, author;

    /**
     * Books Constructor: Takes the given parameters (id, title, author, year, numberCopies) to create a booklist list
     * (Array List to be exact).
     * @param id ID of the given book
     * @param title Title of the given book ID
     * @param author Author of the given book ID
     * @param year Year of book ID publishing
     * @param numberCopies Number of copies available for the given book ID
     */

    public Books(int id, String title, String author, int year, int numberCopies)
    {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.numberCopies = numberCopies;
        //this.remaining = remaining;
    }

    public int getId() {
        return id;
    }

    public String getName()
    {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public int getNumberCopies() {
        return numberCopies;
    }

    public void setNumberCopies(int newNumber){
        this.numberCopies = newNumber;
    }

}

