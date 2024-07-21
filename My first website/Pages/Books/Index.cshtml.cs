using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace Iwanttodie.Pages.Books
{
    public class Index : PageModel
    {
        public List<BookInfo> BookList { get; set; } = new List<BookInfo>();

        public void OnGet(string titleFilter, string authorFilter, string genreFilter)
        {
            try
            {
                string connectionString = "Server=localhost\\SQLEXPRESS;Database=crmdb;Trusted_Connection=True;TrustServerCertificate=True";

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    connection.Open();

                    string sql = "SELECT * FROM books WHERE 1=1";

                    if (!string.IsNullOrEmpty(titleFilter))
                        sql += $" AND title LIKE '%{titleFilter}%'";

                    if (!string.IsNullOrEmpty(authorFilter))
                        sql += $" AND author LIKE '%{authorFilter}%'";

                    if (!string.IsNullOrEmpty(genreFilter))
                        sql += $" AND genre LIKE '%{genreFilter}%'";


                    sql += " ORDER BY id DESC";

                    using (SqlCommand command = new SqlCommand(sql, connection))
                    {
                        using (SqlDataReader reader = command.ExecuteReader())
                        {
                            while (reader.Read())
                            {
                                BookInfo bf = new BookInfo();
                                bf.Id = reader.GetInt32(0);
                                bf.Title = reader.GetString(1);
                                bf.Author = reader.GetString(2);
                                bf.Genre = reader.GetString(3);
                                bf.Price = reader.GetString(4);
                                bf.Quantity = reader.GetString(5);
                                bf.Created_At = reader.GetDateTime(6).ToString("dd/MM/yyyy");
                                bf.Updated_At = reader.GetDateTime(7).ToString("dd/MM/yyyy");

                                BookList.Add(bf);
                            }
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine("An error occurred: " + ex.Message);
            }
        }
    }

    public class BookInfo
    {
        public int Id { get; set; }
        public string Title { get; set; } = "";
        public string Author { get; set; } = "";
        public string Genre { get; set; } = "";
        public string Price { get; set; } = "";
        public string Quantity { get; set; } = "";
        public string Created_At { get; set; } = "";
        public string Updated_At { get; set; } = "";
    }
}

