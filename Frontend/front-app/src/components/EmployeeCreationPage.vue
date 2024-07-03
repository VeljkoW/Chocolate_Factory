<template>
    <div class="top-section">
      <h1>Add an Employee</h1>
      <button class="back-button" @click="goBack">Go back</button>
    </div>
    <div class="table-container1">
      <form @submit.prevent="register">
        <table>
          <tr>
            <td>*Name:</td>
            <td>
              <input type="text" v-model="name" id="nameTextbox" class="input-box" />
            </td>
          </tr>
          <tr>
            <td>*Surname:</td>
            <td>
              <input type="text" v-model="surname" id="surnameTextbox" class="input-box" />
            </td>
          </tr>
          <tr>
            <td>*Gender:</td>
            <td>
              <select v-model="gender" id="genderSelect" class="input-box">
                <option value="Male">Male</option>
                <option value="Female">Female</option>
                <option value="Mechanic">Mechanic</option>
                <option value="Other">Other</option>
              </select>
            </td>
          </tr>
          <tr>
            <td>*Date of Birth:</td>
            <td>
              <input type="date" v-model="dateOfBirth" id="dateOfBirthInput" class="input-box" />
            </td>
          </tr>
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
        <button type="submit">Register</button>
      </form>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        name: '',
        surname: '',
        gender: 'Male',
        dateOfBirth: '',
        username: '',
        password: '',
        role: 'Employee',
        factoryId: -1
      };
    },
    mounted()
    {
        this.factoryId = this.$route.params.id;
    },
    methods: {
      async register() {
        let empty = false;
        if (this.name === '') {
          document.getElementById('nameTextbox').classList.add('errorClass');
          empty = true;
        } else {
          document.getElementById('nameTextbox').classList.remove('errorClass');
        }
        if (this.surname === '') {
          document.getElementById('surnameTextbox').classList.add('errorClass');
          empty = true;
        } else {
          document.getElementById('surnameTextbox').classList.remove('errorClass');
        }
        if (this.gender === '') {
          document.getElementById('genderSelect').classList.add('errorClass');
          empty = true;
        } else {
          document.getElementById('genderSelect').classList.remove('errorClass');
        }
        if (this.dateOfBirth === '') {
          document.getElementById('dateOfBirthInput').classList.add('errorClass');
          empty = true;
        } else {
          document.getElementById('dateOfBirthInput').classList.remove('errorClass');
        }
        if (this.username === '') {
          document.getElementById('usernameTextbox').classList.add('errorClass');
          empty = true;
        } else {
          document.getElementById('usernameTextbox').classList.remove('errorClass');
        }
        if (this.password === '') {
          document.getElementById('passwordTextbox').classList.add('errorClass');
          empty = true;
        } else {
          document.getElementById('passwordTextbox').classList.remove('errorClass');
        }
  
        if (empty) {
          return;
        }
  
        if(this.factoryId == -1)
        {
            alert('failed to fetch factory id');
            return;
        }
        try {
          const response = await axios.put('http://localhost:8080/WebShopAppREST/rest/user/register', {
            name: this.name,
            surname: this.surname,
            gender: this.gender,
            dateOfBirth: this.dateOfBirth,
            username: this.username,
            password: this.password,
            uloga: this.role,
            factoryId: this.factoryId,
            points: 0,
            userTypeId: 1
          });
          if (response.status === 200) {
            alert('Registration successful!');
          }
          this.goBack();
        } catch (error) {
          console.error('Error:', error);
          if (error.response) {
            console.log('Error response:', error.response);
            if (error.response.data === 'Username already exists') {
              alert('Username already exists');
            } else {
              alert('Registration failed. Please try again later.');
            }
          } else if (error.request) {
            console.error('Error request:', error.request);
            alert('No response from the server.');
          } else {
            console.error('An error occurred:', error);
            alert('An unexpected error occurred. Please try again later.');
          }
        }
      },
      goBack() {
        this.$router.push("/factory/" + this.factoryId);
      }
    }
  };
  </script>
  
  <style>
  .table-container1 {
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
  </style>