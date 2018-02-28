import library.Books;
import library.Library;

public class Test {
    public static void main(String[] args) {
        Library lib = new Library("data/books.txt", "data/members.txt",
                "data/bookloans.txt");

//        System.out.println();
//        lib.searchBook("Data");
//        System.out.println();
//        lib.searchMember("Sarah", "Hoopern");
//        lib.returnBook();
//        lib.searchMember();
//        lib.borrowBook();
        lib.addNewBook();
    }
}
