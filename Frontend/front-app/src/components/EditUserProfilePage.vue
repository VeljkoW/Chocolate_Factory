<template>
    <div class="top-section">
      <h1>Edit {{this.username}}'s Profile</h1>
      <button class="back-button" @click="goBack">Go back</button>
    </div>
    <div class="table-container-editprofile">
      <form @submit.prevent="updateProfile">
        <table>
          <tr>
            <td>Name:</td>
            <td>
                <input type="text" v-model="name" id="nameTextbox" class="input-box" />
            </td>
          </tr>
          <tr>
            <td>Surname:</td>
            <td>
                <input type="text" v-model="surname" id="surnameTextbox" class="input-box" />
            </td>
          </tr>
          <tr>
            <td>Gender:</td>
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
            <td>Date of Birth:</td>
            <td>
                <input type="date" v-model="dateOfBirth" id="dateOfBirthInput" class="input-box" />
            </td>
          </tr>
          <tr>
            <td>Username:</td>
            <td>
                <input type="text" v-model="username" id="usernameTextbox" class="input-box" />
            </td>
          </tr>
        </table>
        <button type="submit">Save</button>
      </form>
      <table class="change-password-table">
        <tr>
          <td>Password:</td>
          <td>
            <input type="password" v-model="password" id="passwordTextbox" class="input-box" />
          </td>
        </tr>
        <tr>
          <td>Confirm Password:</td>
          <td>
            <input type="password" v-model="confirmPassword" id="confirmPasswordTextbox" class="input-box" />
          </td>
        </tr>
      </table>
      <button @click="changePassword" class="change-password-button">Change Password</button>
    </div>
  </template>
<script>
import axios from 'axios';

export default {
    data()
    {
        return {
            id: -1,
            name: '',
            surname: '',
            gender: '',
            dateOfBirth: '',
            username: '',
            password: '',
            confirmPassword: ''
        }
    },
    mounted()
    {
        this.getUser();
    },
    methods:
    {
        getUser()
        {
            this.id = this.$route.params.id
            
            if(this.id == -1)
            {
                alert('user not found');
                return;
            }
            axios.get('http://localhost:8080/WebShopAppREST/rest/user/getById?id=' + this.id).then(response => {
                this.name = response.data.name;
                this.surname = response.data.surname;
                this.gender = response.data.gender;
                this.dateOfBirth = response.data.dateOfBirth;
                this.username = response.data.username;
            }).catch(error => {
                alert(error.response.data);
            });
        },
        goBack()
        {
            this.$router.push("/userprofile/"+ this.id);
        },
        async updateProfile()
        {
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
    
          if (empty) {
            return;
          }

          try {
            const response = await axios.put('http://localhost:8080/WebShopAppREST/rest/user/updateWithoutPassword', {
                  id: this.id,
                  name: this.name,
                  surname: this.surname,
                  gender: this.gender,
                  dateOfBirth: this.dateOfBirth,
                  username: this.username
              });
            if (response.status === 200) {
              alert('User updated successfully!');
            }
          } catch (error) {
            console.error('Error:', error);
            if (error.response) {
              console.log('Error response:', error.response);
              if (error.response.data === 'Username already exists') {
                alert('Username already exists');
              } else {
                alert('Update failed. Please try again later.');
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
        async changePassword()
        {
          let empty = false;
          if (this.password === '') {
            document.getElementById('passwordTextbox').classList.add('errorClass');
            empty = true;
          } else {
            document.getElementById('passwordTextbox').classList.remove('errorClass');
          }
          if (this.confirmPassword === '') {
            document.getElementById('confirmPasswordTextbox').classList.add('errorClass');
            empty = true;
          } else {
            document.getElementById('confirmPasswordTextbox').classList.remove('errorClass');
          }
          if(empty)
          {
            return;
          }
          if(this.confirmPassword != this.password)
          {
            alert("The passwords do not match!");
            return;
          }
          try {
            const response = await axios.put('http://localhost:8080/WebShopAppREST/rest/user/updatePassword', {
                  id: this.id,
                  password: this.password
              });
            if (response.status === 200) {
              alert('Password updated successfully!');
            }
          } catch (error) {
            console.error('Error:', error);
            if (error.response) {
              console.log('Error response:', error.response);
              alert('Update failed. Please try again later.');
            } else if (error.request) {
              console.error('Error request:', error.request);
              alert('No response from the server.');
            } else {
              console.error('An error occurred:', error);
              alert('An unexpected error occurred. Please try again later.');
            }
          }

        }
    }


}
</script>
<style>

.table-container-editprofile {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: calc(100vh - 40px - 40px);
  margin-top: 40px;
}
.change-password-table {
  margin-top: 50px;
}

.change-password-button {
  background-color: #4caf50;
  color: white;
  margin-top: 10px;
}
</style>