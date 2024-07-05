<template>
    <div class="top-section">
      <h1>{{this.username}}'s Profile</h1>
      <button class="back-button" @click="goBack">Go back</button>
    </div>
    <div class="table-container-profile">
      <form @submit.prevent="edit">
        <table>
          <tr>
            <td>Name:</td>
            <td>
                {{ this.name }}
            </td>
          </tr>
          <tr>
            <td>Surname:</td>
            <td>
                {{ this.surname }}
            </td>
          </tr>
          <tr>
            <td>Gender:</td>
            <td>
                {{ this.gender }}
            </td>
          </tr>
          <tr>
            <td>Date of Birth:</td>
            <td>
                {{ this.dateOfBirth }}
            </td>
          </tr>
          <tr>
            <td>Username:</td>
            <td>
                {{ this.username }}
            </td>
          </tr>
          <tr>
            <td>Role:</td>
            <td>
                {{ this.uloga }}
            </td>
          </tr>
          <tr>
            <td>Points:</td>
            <td>
                {{ this.points }}
            </td>
          </tr>
          <tr>
            <td>User type:</td>
            <td>
                {{ this.getUserTypeName(this.userTypeId) }}
            </td>
          </tr>
        </table>
        <button type="submit">Edit Profile</button>
      </form>
      <div>
        <button class="delete-account-button" @click="confirmDeleteAccount">Delete account</button>
      </div>
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
            username: '',
            gender: '',
            dateOfBirth: '',
            uloga: '',
            points: 0,
            userTypeId: -1,
            userType: '',
            usertypes: []
        }
    },
    mounted()
    {
        this.getUserTypes()
        this.getUser();
    },
    methods: {
      goBack() {
        this.$router.push('/');
      },
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
                this.username = response.data.username;
                this.dateOfBirth = response.data.dateOfBirth;
                this.gender = response.data.gender;
                this.uloga = response.data.uloga;
                this.points = response.data.points;
                this.userTypeId = response.data.userTypeId;
            }).catch(error => {
                alert(error.response.data);
            });
      },
      getUserTypeName(userTypeId) {
          const userType = this.usertypes.find(u => u.id === userTypeId);
          return userType ? userType.name : 'Unknown';
      },
      getUserTypes()
      {
        axios.get('http://localhost:8080/WebShopAppREST/rest/usertype/getall').then(response => {
            this.usertypes = response.data;
        }).catch(error => {
                  alert('Error fetching usertypes')
              });
      },
      async edit()
      {
        this.$router.push('/edituserprofile/' + this.id);
      },
      confirmDeleteAccount()
      {
        if(confirm('Are you sure you want to delete your account?'))
        {
          this.deleteAccount();
        }        
      },
      deleteAccount()
      {
        axios.put('http://localhost:8080/WebShopAppREST/rest/user/deleteUser?id=' + this.id).then(response => {
          //add logout part AAAAAAAAAAAAA
          this.$router.push('/');
        }).catch(error => {
                  alert('Error deleting user')
              });
      }
    }
}
</script>
<style>
.table-container-profile {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: calc(100vh - 40px - 40px);
  margin-top: -70px;
}
.delete-account-button {
  background-color: red !important;
  color: white !important;
  border-radius: 4px !important;
  border: none !important;
  cursor: pointer !important;
  margin: 30px !important;
}

.delete-account-button:hover {
  background-color: darkred !important;
}
</style>