INSERT INTO languages (code, name, file_extension, example_code)
VALUES
('java', 'Java', '.java',
 'public class Main {
     public static void main(String[] args) {
         System.out.println("Hello, World!");
     }
 }'),

('cpp', 'C++', '.cpp',
 '#include <iostream>
 using namespace std;

 int main() {
     cout << "Hello, World!" << endl;
     return 0;
 }'),

('c', 'C', '.c',
 '#include <stdio.h>

 int main() {
     printf("Hello, World!\\n");
     return 0;
 }'),

('go', 'Go', '.go',
 'package main

 import "fmt"

 func main() {
     fmt.Println("Hello, World!")
 }'),

('python', 'Python', '.py',
 'print("Hello, World!")');