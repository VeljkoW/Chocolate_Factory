<template>
    <div class="top-section">
        <h1>Login</h1>
        <button class="back-button" @click="goBack">Go back</button>
    </div>
    <div class="table-container-login">
        <form @submit.prevent="login">
          <table>
            <tr>
              <td>*Username:</td>
              <td>
                <input type="text" v-model="username" id="usernameTextbox" class="input-box" />
              </td>
            </tr>
            <tr>
              <td>*Password:</td>
              <td>
                <input type="password" v-model="password" id="passwordTextbox" class="input-box" />
              </td>
            </tr>
          </table>
          <button type="submit">Login</button>
          <p>Don't have an account? <router-link to="/register">Register</router-link></p>
        </form>
      </div>
</template>
<script>
import axios from 'axios';
export default {
    data() {
        return {
            username: '',
            password: ''
        };
    },
    methods: {
        async login()
        {
            let empty = false;
            if(this.username === '')
            {
                document.getElementById('usernameTextbox').classList.add('errorClass');
                empty=true;
            }
            else
            {
                document.getElementById('usernameTextbox').classList.remove('errorClass');
            }
            if(this.password === '')
            {
                document.getElementById('passwordTextbox').classList.add('errorClass');
                empty=true;
            }
            else
            {
                document.getElementById('passwordTextbox').classList.remove('errorClass');
            }
            if(empty)
            {
                return;
            }

            try
            {
                const response = await axios.post('http://localhost:8080/WebShopAppREST/rest/user/login/', {
                    username: this.username,
                    password: this.password
                });
                if(response.status === 200)
                {
                    alert('Login successful!');
                }
            }
            catch (error)
            {
                console.error('Error response:', error.response);
                if(error.response)
                {
                if (error.response.status === 401)
                {
                    if(error.response.data === "User does not exist") 
                    {
                        alert('User does not exist');
                    }
                    else if(error.response.data === "Incorrect password")
                    {
                        alert('Incorrect password');
                    }
                    else if(error.response.data === "Account has been blocked.")
                    {
                      alert('Account has been blocked.');
                    }
                }
                else
                {
                    console.error('An error occured:',error);
                }
            }
            else if (error.request) {
                console.error('Error request:', error.request);
                alert('No response from the server.');
            }
            else
            {
                console.error('An error occured:',error);
                alert('An unexpected error occurred. Please try again later.');
            }
            }
        },
        goBack()
        {
            this.$router.push("/");
        }
    }
}

</script>
<style>
.table-container-login {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: calc(100vh - 40px - 40px);
    margin-top: -40px;
  }
  
  table {
    border-collapse: collapse;
    margin: 20px 0;
    font-size: 18px;
    min-width: 400px;
    background-color: #f9f9f9;
    border-radius: 8px;
    overflow: hidden;
    box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
  }
  
  table tr {
    background-color: #f8f8f8;
    border-bottom: 1px solid #dddddd;
  }
  
  table th,
  table td {
    padding: 12px 15px;
    text-align: left;
  }
  
  table th {
    background-color: #f2f2f2;
    font-weight: bold;
  }
  
  table tr:nth-of-type(even) {
    background-color: #f3f3f3;
  }
  .input-box {
    width: 100%;
    padding: 10px;
    margin: 8px 0;
    box-sizing: border-box;
    border: 1px solid #ccc;
    border-radius: 4px;
  }

  .register-link {
    margin-top: 10px;
    text-align: center;
  }
  
  .register-link p {
    margin: 0;
  }
.register-link router-link {
    color: #007bff;
    cursor: pointer;
    text-decoration: underline;
  }
</style>