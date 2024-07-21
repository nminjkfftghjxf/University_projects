using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Data.SqlClient;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.Extensions.Logging;

namespace IwishIwasDead.Pages.Admin
{
    public class CreateAccount : PageModel
    {
        public int Id{get;set;}
        
        [BindProperty,Required(ErrorMessage = "Email is required!")]
        [EmailAddress(ErrorMessage = "Insert a valid email!")]
        public string Email{get;set;}="";

        [BindProperty,Required(ErrorMessage = "Password is required")]
        [RegularExpression(@"^(?=.*[A-Z])(?=.*[!@#$%^&*])(?=.*[a-z]).{8,}$", ErrorMessage = "Password must have at least one capital letter and special character and to be at least 8 characters long")]
        public string Password{get;set;}="";

        public string ErrorMessage{get;set;}="";

        public void OnGet()
        {
           
        }

        public void OnPost()
        {
            if(!ModelState.IsValid)
            {
                return;        
            }

            try{
                string connectionString ="Server=localhost\\SQLEXPRESS;Database=crmdb;Trusted_Connection=True;TrustServerCertificate=True";
                using (SqlConnection connection=new SqlConnection(connectionString))
                {
                 connection.Open();

                 string sql="INSERT INTO users "+
                 "(email,password) VALUES"+" (@email,@password);";  

                 using( SqlCommand command=new SqlCommand(sql,connection))
                 {
                    command.Parameters.AddWithValue("@email",Email);
                    command.Parameters.AddWithValue("@password",Password);

                    command.ExecuteNonQuery();
                 } 
                }

            }catch(Exception ex)
            {
                ErrorMessage=ex.Message;
                return;
            }

            Response.Redirect("/Admin/Login");
        }
    }
}