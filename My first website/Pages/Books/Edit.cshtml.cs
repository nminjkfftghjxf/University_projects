using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Data.SqlClient;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.Extensions.Logging;

namespace Iwanttodie.Pages.Books
{
    public class Edit : PageModel
    {
        [BindProperty]
        public int Id {get;set;}

        [BindProperty,Required(ErrorMessage ="Title cannot be empty!")]
        public string Title{get;set;} ="";

               
        [BindProperty,Required(ErrorMessage ="Author cannot be empty!")]
        public string Author{get;set;} ="";


        [BindProperty,Required(ErrorMessage ="Genre cannot be empty!")]
        public string Genre{get;set;}="";

        [BindProperty,Required(ErrorMessage ="Price cannot be empty!")]  
        public string Price{get;set;}="";

        [BindProperty,Required(ErrorMessage ="Quantity cannot be empty!")]
        public string Quantity{get;set;}="";



        public string ErrorMessage{get;set;}="";

        public void OnGet(int id)
        {
            try{

                string connectionString="Server=localhost\\SQLEXPRESS;Database=crmdb;Trusted_Connection=True;TrustServerCertificate=True";

                using (SqlConnection connection=new SqlConnection(connectionString))
                {
                    connection.Open();
                    string sql="SELECT * FROM books WHERE id=@id";
                    
                    using (SqlCommand command = new SqlCommand(sql,connection))
                    {
                        command.Parameters.AddWithValue("@id",id);

                      using (SqlDataReader reader=command.ExecuteReader())
                      {
                        if(reader.Read())
                        {
                        Id=reader.GetInt32(0);
                        Title=reader.GetString(1);
                        Author=reader.GetString(2);
                        Genre=reader.GetString(3);
                        Price=reader.GetString(4);
                        Quantity=reader.GetString(5);
                        
                        }else
                        {
                            Response.Redirect("/Books/Index");
                        }

                      }
                    }    
                }

            }catch(Exception ex)
            {
                ErrorMessage=ex.Message;
                return;
            }           

        }

         public void OnPost()
            {
                if(!ModelState.IsValid)
                {
                    return ;
                }

                try{
                    string connectionString="Server=localhost\\SQLEXPRESS;Database=crmdb;Trusted_Connection=True;TrustServerCertificate=True";

                    using (SqlConnection connection=new SqlConnection(connectionString))
                    {
                        connection.Open();

                        string sql= "UPDATE books SET title=@title, author=@author, genre=@genre, price=@price, quantity=@quantity WHERE id=@id";

                        using (SqlCommand command=new SqlCommand(sql,connection))
                        {
                            command.Parameters.AddWithValue("@title",Title);
                            command.Parameters.AddWithValue("@author",Author);
                            command.Parameters.AddWithValue("@genre",Genre);
                            command.Parameters.AddWithValue("@price",Price);
                            command.Parameters.AddWithValue("@quantity",Quantity);
                            command.Parameters.AddWithValue("@id",Id);

                            command.ExecuteNonQuery();
                        }
                    }
                }catch(Exception ex)
                {
                    ErrorMessage=ex.Message;
                    return;
                }

                Response.Redirect("/Books/Index");
            }
    }
}

