using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.Extensions.Logging;

namespace Iwanttodie.Pages.Books
{
    public class Delete : PageModel
    {
    

        public void OnGet()
        {
        }

        public void OnPost(int id)
        {
            deleteBook(id);
            Response.Redirect("/Books/Index");
        }

        private void deleteBook(int id)
        {
            try{
                string connectionString="Server=localhost\\SQLEXPRESS;Database=crmdb;Trusted_Connection=True;TrustServerCertificate=True";
                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    connection.Open();

                    string sql="DELETE FROM books WHERE id=@id";

                    using(SqlCommand command=new SqlCommand(sql,connection))
                    {
                        command.Parameters.AddWithValue("@id",id);
                        command.ExecuteNonQuery();
                    }
                }

            }catch(Exception ex)
            {
                Console.WriteLine("This book does not want to be deleted!"+ex.Message);
            }
        }
    }
}