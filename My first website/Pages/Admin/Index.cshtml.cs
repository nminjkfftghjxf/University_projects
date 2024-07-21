using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.AspNetCore.SignalR;
using Microsoft.Extensions.Logging;
using SQLitePCL;

namespace IwishIwasDead.Pages.Admin
{
    public class Index : PageModel
    {
        public List<AdminInfo> AdminList{get;set;}=[];
        
         public void OnGet()
        {
            try{
                string connectionString="Server=localhost\\SQLEXPRESS;Database=crmdb;Trusted_Connection=True;TrustServerCertificate=True";

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    connection.Open();
                    string sql="SELECT * FROM users";

                    using(SqlCommand command=new SqlCommand(sql,connection))
                    {
                        using(SqlDataReader reader =command.ExecuteReader())
                        {
                            while(reader.Read())
                            {
                                AdminInfo ai= new AdminInfo();
                                ai.Id=reader.GetInt32(0);
                                ai.Email=reader.GetString(1);
                                ai.Password=reader.GetString(2);
                                ai.CreatedAt=reader.GetString(3);
                                ai.UpdatedAt=reader.GetString(4);

                                AdminList.Add(ai);
                            }
                        }
                    }

                }
            }catch(Exception ex)
            {
                Console.WriteLine("Something went wront with the registration/login part"+ex.Message);
            }
        }

        public class AdminInfo
        {
            public int Id{get;set;}
            public string Email{get;set;}="";
            public string Password{get;set;}="";
            public string CreatedAt{get;set;}="";
            public string UpdatedAt{get;set;}="";
        }
    }
}