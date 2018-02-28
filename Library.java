package library;

import java.io.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Program formulates a library management application
 * @author Brandon Indradjaja
 * @author Kamila Hoffmann
 */

public class Library {
    //ask for sure if all should be in library class, because in class she wanted 3 different classes
    //ask if show all members and show all book loans, should be in the same file and cant get imported.
    // Create an array list for books.txt
    // Create an array list for borrowers
    ArrayList<Books> booklist = new ArrayList<>();
    ArrayList<Members>memberlist = new ArrayList<>();
    ArrayList<BookLoans>bookloanlist = new ArrayList<>();

    //Constructor is the function that has the same name as the class.
    public Library(String booklistFileName, String memberlistFileName, String bookloanlistFileName) {
        String line;
        try {
            //For the book list
            BufferedReader info_books = new BufferedReader(new FileReader(booklistFileName));
            //For the member list
            BufferedReader info_members = new BufferedReader(new FileReader(memberlistFileName));
            //For the book loans list
            BufferedReader info_book_loans = new BufferedReader(new FileReader(bookloanlistFileName));
            while ((line = info_books.readLine()) != null) {
                //Create a new list where all commas will be removed for books list
                String[] lineArray1 = line.split(",");
                booklist.add(new Books(
                        Integer.parseInt(lineArray1[0]),
                        lineArray1[1],
                        lineArray1[2],
                        Integer.parseInt(lineArray1[3]),
                        Integer.parseInt(lineArray1[4])
                ));
                // Prints out book list
                //System.out.println(line);
            }
            //System.out.println();

            while ((line = info_members.readLine()) != null) {
                //Create a new list where all commas will be removed for members list
                String[] lineArray2 = line.split(",");
                memberlist.add(new Members(
                        Integer.parseInt(lineArray2[0]),
                        lineArray2[1],
                        lineArray2[2],
                        lineArray2[3]
                ));
                // Prints out members list
                //System.out.println(line);
            }
            //System.out.println();

            while ((line = info_book_loans.readLine()) != null) {
                //Create a new list where all commas will be removed for book loans list
                String[] lineArray3 = line.split(",");
                bookloanlist.add(new BookLoans(
                        Integer.parseInt(lineArray3[0]),
                        Integer.parseInt(lineArray3[1]),
                        Integer.parseInt(lineArray3[2]),
                        LocalDate.parse(lineArray3[3])
                ));
                // Prints out book loans list
                //System.out.println(line);
            }

            info_books.close();
            info_members.close();
            info_book_loans.close();

        } catch(FileNotFoundException ex){
            System.out.println(booklistFileName + "Not Found");
            System.out.println(memberlistFileName + "Not Found");
            System.out.println(bookloanlistFileName + "Not Found");
        } catch(IOException e){
            System.out.println("I/O errors");
            e.printStackTrace();
        }
    }


    // searchBook() User Input
    public void searchBook(){
        System.out.println("Specify Book Name:");
        Scanner input = new Scanner(System.in);
        String search_bookname = input.next();
        searchBook(search_bookname);
    }

    public void searchBook(String bookname) {
        ArrayList<Books> Results_Books = new ArrayList<>();
        if (bookname.isEmpty()) {
            System.out.println("Please type in something...");
        }
        else if (!bookname.isEmpty()) {
//            System.out.printf("%-12s %-27s %-27s %-10s %-2s\n", "ID", "Title", "Author", "Year", "Number of Copies");
            for (Books book : booklist) {
                // If new list is empty, you add the book that your string bookname is looking for
                if (book.getName().contains(bookname)) {
                    Results_Books.add(book);
                    System.out.printf("%-12s %-27s %-27s %-10s %-2s\n",
                            book.getId(), book.getName(), book.getAuthor(),
                            book.getYear(), book.getNumberCopies());
                }
            }
        }
        if (Results_Books.isEmpty()) {
            System.out.println("Book is not found...");
        }
    }

    // searchMember() User Input
    public void searchMember(){
        System.out.println("Specify Member Name:");
        Scanner input = new Scanner(System.in);
        String search_memberfname = input.next();
        String search_memberlname = input.next();
        searchMember(search_memberfname, search_memberlname);
    }

    public void searchMember(String type_first, String type_last){
        // Besides the basic information of each member, the output should also includes his/her book loan information,
        // such as how many books borrowed, the book titles, the borrowing dates, due dates and fine warnings if any
        // overdue books.
        ArrayList<Members> Results_Members = new ArrayList<>();
        if (type_first.isEmpty() && type_last.isEmpty()) {
            System.out.println("Please type in something...");
        }
        else if(!type_first.isEmpty() && !type_last.isEmpty()) {
            for (Members member : memberlist) {
                // If new list is empty, you add the member name that your string type_first is looking for
                if (member.getfName().contains(type_first) && member.getlName().contains(type_last)) {
                    int memberId = member.getId();
                    ArrayList<ArrayList> memberInfo = new ArrayList<>();
                    int count = 0;
                    for (BookLoans bookloan : bookloanlist) {
                        if (memberId == (bookloan.getMemberID())) {
                            ArrayList<String> oneLine = new ArrayList<>();
                            count += 1; // counting how many books borrowed
                            String bookTitle = "";
                            for (Books each : booklist) {
                                if (bookloan.getBookID() == each.getId()) {
                                    bookTitle = each.getName();
                                }
                            }
                            // Book ID (Later into book title)
                            oneLine.add(bookTitle);
                            // Borrowed Date
                            oneLine.add((bookloan.getBorrowDate().toString()));
                            // Due Date for borrowed book
                            oneLine.add(bookloan.getBorrowDate().plus(30, ChronoUnit.DAYS).toString());
                            // Add Fine Warnings if any
                            if (bookloan.fineCalc() != 0.0f) {
                                String yesFine = "You have a due fine for this book!";
                                oneLine.add(yesFine);
                                //oneLine.add(Float.toString(bookloan.fineCalc()));
                            }
                            else {
                                // Otherwise a notice would also be added that there are no fines
                                String noFine = "No fine";
                                oneLine.add(noFine);
                            }
                            memberInfo.add(oneLine);
                        }
                    }
                    // Prints out all the relevant details to task 3
                    System.out.println();
                    System.out.printf("%-12s %-12s %-12s %-15s %-20s\n", "ID", "First Name", "Last Name", "Date Joined",
                            "Number of Books Borrowed ");
                    Results_Members.add(member);
                    System.out.printf("%-12s %-12s %-12s %-15s %-20s \n",
                            member.getId(), member.getfName(), member.getlName(),
                            member.getDateJoin(), count);
                    System.out.println();
                    System.out.println("Book Titles and Borrowed Dates:");
                    for (ArrayList i : memberInfo) {
                        System.out.println(i);
                    }
                }
            }
        }
        if (Results_Members.isEmpty()) {
            System.out.println("The full name of the member cannot be found. " +
                    "Please type the correct full name again...");
        }
    }

    public int availableCopies(Books aBook) {
        int first = aBook.getNumberCopies();
        int idBook = aBook.getId();
        int borrowed = 0;
        for (BookLoans each : bookloanlist) {
            if (idBook == each.getId()) {
                borrowed ++;
            }
        }
        int copies = first - borrowed;
        return copies;
    }

    // returnBook() User Input
    public void returnBook(){
        System.out.println("Specify the Book Loan ID:");
        Scanner input = new Scanner(System.in);
        int bookloanID = input.nextInt();
        // Passes user input
        returnBook(bookloanID);
    }

    public void returnBook(int bookloanId){
        boolean isThere = false;
        int index = 0;
        BookLoans theBookLoan = new BookLoans(bookloanId);
        for (BookLoans each : bookloanlist) {
            if (each.getId() == bookloanId) {
                isThere = true;
                theBookLoan = each;
                break;
            }
            index ++;
        }
        if (isThere == false) {
            System.out.println("You have never borrowed this book.");
        }
        else {
            float fine = theBookLoan.fineCalc();
            if (fine == 0) {
                System.out.println("Book has been returned.");
                for (Books each : booklist) {
                    if (each.getId() == theBookLoan.getBookID()) {
                        int Copies = 1;
                        Copies += each.getNumberCopies();
                        each.setNumberCopies(Copies);
                    }
                }
                bookloanlist.remove(index);
            }
            else {

                System.out.println("There is a fine and it is: " + fine);
                System.out.println("Will you pay this fine? y/n");
                Scanner scan = new Scanner(System.in);
                String User = scan.next();
                System.out.println();
                if(User.equals("y")) {
                    System.out.println("Book has been returned.");
                    for (Books each : booklist) {
                        if (each.getId() == theBookLoan.getBookID()) {
                            int Copies = 1;
                            Copies += each.getNumberCopies();
                            each.setNumberCopies(Copies);
                        }
                    }
                    bookloanlist.remove(index);
                }
                else {
                    System.out.println("Book has not been returned.");
                }
            }
        }
    }
    public void showAllBooks(){

    }
    public void showAllMembers(){

    }
    public void showAllBookLoans(){

    }

    // borrowBook() User Inputs
    public void borrowBook() {
        Scanner user_input = new Scanner(System.in);
        System.out.println("Type in the Book You Wish to Borrow:");
//        Console console = System.console();
        String book_title = user_input.nextLine();
        System.out.println("Type in the Member You Are Looking For:");
        String member_name = user_input.nextLine();
        borrowBook(book_title, member_name);
    }

    public void borrowBook(String title, String MemberName) {
//        // Call method for availableCopies() method
//        // Check if book exists, using search for member and search for book methods
//        int memberID = -1;
//        int bookID = -1;
//        int count = 0;
//        for (Books book : booklist) {
//            if (book.getName().equals(title)) {
//                // Checks if available copies is not 0 for a book
//                if(availableCopies(book) != 0) {
//                    System.out.println("Available Copies for " + book + ": " + availableCopies(book));
//                }
//                bookID = book.getId();
//            }
//        }
//        for (Members member : memberlist){
//            if (member.getfName().equals(MemberName) && member.getlName().equals(MemberName)){
//                //Checks if member has reached his/her maximum borrowing quantities
//                memberID = member.getId();
//            }
//        }
//        for (BookLoans bookloans : bookloanlist){
//            // do == when its involving integers
//            if (memberID == bookloans.getMemberID()){
//                count += 1;
//            }
//        }
//        if (count < 5){
//            // Gets last ID in the list, gets the bookloanID and adds 1 into it
//            int nextID = bookloanlist.get(bookloanlist.size() - 1).getId() + 1;
//            BookLoans loan = new BookLoans(nextID, bookID, memberID, LocalDate.now());
//            bookloanlist.add(loan);
//            System.out.println("You got a loan...");
//        }
//        else{
//            System.out.println("You cannot loan out anymore books, unfortunately.");
//        }
//    }
        LocalDate borrowedDate = LocalDate.now();
        boolean foundBook = false;
        boolean enoughCopies = false;
        boolean foundMember = false;
        boolean maxBooks = false;
        int theBookId = 0;
        int theMemberId = 0;
        for (Books b : booklist) {
            if (b.getName().equals(title)) {
                foundBook = true;
                theBookId = b.getId();
                if (b.getNumberCopies() > 0) {
                    enoughCopies = true;
                }
            }
        }
        int count = 0;

        for (Members m : memberlist) {
            if ((m.getfName() + " " + m.getlName()).equals(MemberName)) {
                foundMember = true;
                theMemberId = m.getId();
                for (BookLoans bookloan : bookloanlist) {
                    if (theMemberId == (bookloan.getMemberID())) {
                        count += 1; // counting how many books borrowed
                    }
                }
                if (count < 5) {
                    maxBooks = true;
                }
            }
        }
        if (foundMember == false) {
            System.out.println("Sorry, the member does not exist.");
        } else if (foundBook == false) {
            System.out.println("Sorry, this book does not exist.");
        } else if (enoughCopies == false) {
            System.out.println("Sorry, there are no more available copies");
        } else if (maxBooks == false) {
            System.out.println("Sorry, each member can borrow at most 5 books.");
        } else {
            System.out.println("The book was successfully borrowed.");
            //300013,100004,200006,2018-02-13
            int index = bookloanlist.get(bookloanlist.size() - 1).getId() + 1;
            BookLoans newBookLoan = new BookLoans(index, theBookId, theMemberId, borrowedDate);
            bookloanlist.add(newBookLoan);
        }
        //Checks if new book is there
//        for (BookLoans loan : bookloanlist) {
//            System.out.println(loan.getId() + " " + loan.getBookID() + " " + loan.getMemberID() + " " + loan.getBorrowDate());
//        }
    }

    // addNewBook() User Inputs
    public void addNewBook(){
        Scanner add = new Scanner(System.in);

        System.out.println("Specify Book Title:");
        String book_title = add.nextLine();

        System.out.println("Specify Author of Book:");
        String author_name = add.nextLine();
        String[] authorNames = author_name.split(",");

        System.out.println("Specify Year of Publishing of Book:");
        int publishing_year = add.nextInt();

        System.out.println("Specify How Many Copies You Wish to Add:");
        int add_copies = add.nextInt();
        addNewBook(book_title, authorNames, publishing_year, add_copies);
    }

    public void addNewBook(String title, String[] authorNames, int year, int numberCopies){
        if(!booklist.isEmpty()){
            for(Books book : booklist){
                if(book.getName().contains(title)){
                    boolean isThere = true;
                }
            }
            System.out.println("The Book You Specified Exists!");
        }
        else {
            System.out.println("Book Archives List is Empty!");
        }
        //User Input for Yes or No to Add New Book
        System.out.println("Would You Like to Continuing Adding This New Book?");
        System.out.println("{y/n]");
        Scanner new_input = new Scanner(System.in);
        String user_input = new_input.next();
        if(user_input.equals("y")) {
            for(Books book : booklist) {
                for(String author_name : authorNames) {
                    if(book.getName().contains(title) && book.getAuthor().contains(author_name)) {
                        booklist.add(book);
                    }
                }
                System.out.println(book.getId() + " " + book.getName() + " " + book.getAuthor() + " " + book.getYear() + " " + book.getNumberCopies());
            }
            System.out.println("The Book Has Been Successfully Added");
        } else if (user_input.equals("n")) {
            System.out.println("You Have Cancelled the Book Addition Service");
        }
    }

    //addNewMember() User Inputs
    private static String addNewMember(){
        Scanner user_input = new Scanner(System.in);
        System.out.println("Specify Member's First Name and Last Name:");
        String fName = user_input.next();
        String lName = user_input.next();
        return fName + lName;

    }

    //Also known as overloading as they are the same class method
    public void addNewMember(String fname, String lname){
        String fName = addNewMember();
        String lName = addNewMember();
        for (Members member : memberlist){
            //Add member relevant information into the memberlist
        }
    }

    // changeQuantity() User Inputs
    private static String changeQuantity(){
        Scanner user_input = new Scanner(System.in);
        System.out.println("Please Indicate Book Title:");
        String book_title = user_input.next();
        System.out.println("Please Indicate the No. of Copies You Would Like to Increase/Decrease:");
        int numCopies = user_input.nextInt();
        return book_title + numCopies;
    }

    //Copy of changeQuantity with 2 arguments
    public void changeQuantity(String title, int numberCopies){

    }


    public void saveChanges(String booklist, String memberlist, String bookloanlist){

    }
}
