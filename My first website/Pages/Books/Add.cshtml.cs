using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Data.SqlClient;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.Extensions.Logging;
using static System.Runtime.InteropServices.JavaScript.JSType;

namespace Iwanttodie.Pages.Books
{
    public class Add : PageModel
    {       
            [BindProperty]
            public int Id { get; set; }
            
            [BindProperty,Required(ErrorMessage ="This cannot be empty!")]
            public string Title{get;set;} =" ";

               
            [BindProperty,Required(ErrorMessage ="This cannot be empty!")]
            public string Author{get;set;} =" ";


            [BindProperty,Required(ErrorMessage ="This cannot be empty!")]
            public string Genre{get;set;}=" ";

            [BindProperty,Required(ErrorMessage ="This cannot be empty!")]
            public string Price{get;set;}=" ";

            [BindProperty,Required(ErrorMessage ="This cannot be empty!")]
            public string Quantity{get;set;}=" ";
            
        public void OnGet()
        {
        }

        public string ErrorMessage {get;set;}=" ";

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
                    string sql="INSERT INTO books"+
                    "(title,author,genre,price,quantity)VALUES "+
                    "(@title,@author,@genre,@price,@quantity);";

                    using (SqlCommand command = new SqlCommand(sql,connection))
                    {
                        command.Parameters.AddWithValue("@Title",Title);
                        command.Parameters.AddWithValue("@Author",Author);
                        command.Parameters.AddWithValue("@Genre",Genre);
                        command.Parameters.AddWithValue("@Price",Price);
                        command.Parameters.AddWithValue("@Quantity",Quantity);

                        command.ExecuteNonQuery();
                    }    
                }

            }catch(Exception ex)
            {
                ErrorMessage=ex.Message;
                return ;
            }

            Response.Redirect("/Books/Index");
        }

    }
}

