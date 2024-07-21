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
    public class Login : PageModel
    {

        public string Email{get;set;}="";

        public string Password{get;set;}="";

        public string ErrorMessage { get; set; } = "";

        public void OnGet()
        {
        }

        public IActionResult OnPost()
        {
            if(!ModelState.IsValid)
            {
                return Page();
            }
            
            try{

                string connectionString ="Server=localhost\\SQLEXPRESS;Database=crmdb;Trusted_Connection=True;TrustServerCertificate=True";

                using (SqlConnection connection =new SqlConnection(connectionString))
                {
                   connection.Open();
                   string sql="SELECT id FROM users WHERE email=@email AND password=@password";
                    
                   using (SqlCommand command = new SqlCommand(sql,connection))
                   {
                    command.Parameters.AddWithValue("@email",Email);
                    command.Parameters.AddWithValue("@password",Password);
                    

                    using (SqlDataReader reader=command.ExecuteReader())
                    {
                        if(reader.Read())
                        {
                            int userId=reader.GetInt32(0);

                            if(userId>0)
                            {
                                return RedirectToPage("/Books/Index");
                            }else
                            {
                                ErrorMessage="The password was not found in the database!";
                                return Page();
                            }
                        }else
                        {
                            ErrorMessage="Invalid email or password!";
                            return Page();
                        }
                    }

                   }
                }

            }catch(Exception ex)
            {
                ErrorMessage="Could not login!"+ex.Message;
                return Page();

            }
        }
    }
}

