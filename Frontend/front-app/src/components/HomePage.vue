<template>
<div>
    <div class="top-section">
        <button class="profile-button" @click="navigateToProfile">My profile</button>
        <button class="usersview-button" @click="navigateToUsers">View all users</button>
        <button class="createfactory-button" @click="navigateToCreateFactory">Create Factory</button>
        <button class="login-button" @click="navigateToLogin">Log in</button>
        
    </div>

    <div class="factory-cards">
        <div v-for="factory in factoriesWithDetailsComputed" :key="factory.id" class="factory-card-link" @click="navigateToFactory(factory.id)">
            <FactoryCard  :factory="factory" :address="factory.address" class="factory-card"/>
        </div>
    </div>
</div>
</template>

<script>
import FactoryCard from './FactoryCard.vue';
import axios from 'axios';

export default {
    name: 'HomePage',
    components: {
        FactoryCard
    },
    data()
    {
        return {
            factories: [],
            locations: [],
            addresses: [],
            factoriesWithDetails: []
        }
    },
    mounted()
    {
        this.getfactories();
        this.getlocations();
        this.getAddresses();
    },
    methods: {
        getfactories(){
            axios.get('http://localhost:8080/WebShopAppREST/rest/factory').then(response => {
                this.factories = response.data;
                console.log('Factories:',this.factories);
            }).catch(error =>
                {
                    console.log('Error fetching factories:',error);
                    alert(error.response.data);
                }
            );
        },
        getlocations(){
            axios.get('http://localhost:8080/WebShopAppREST/rest/location').then(response => {
                this.locations = response.data;
                console.log('Locations:',this.locations);
            }).catch(error =>{
                console.error('Error fetching locations:',error);
                alert(error.response.data);
            });
        },
        getAddresses(){
            axios.get('http://localhost:8080/WebShopAppREST/rest/address').then(response => {
                this.addresses = response.data;
                console.log('Adresses', this.addresses);
            }).catch(error => {
                console.error('Error fetching addresses:',error);
                alert(error.response.data);
            });
        },
        navigateToFactory(factoryId)
        {
            this.$router.push("/factory/" + factoryId);
        },
        navigateToLogin()
        {
            this.$router.push("/login");
        },
        navigateToCreateFactory()
        {
            this.$router.push("/factorycreate");
        },
        navigateToUsers()
        {
            this.$router.push("/users");
        },
        navigateToProfile()
        {
            this.$router.push("/userprofile/" + "2") //change into logged user id when it is implemented
        }
    },
    computed: {
    factoriesWithDetailsComputed() {
      const factoriesWithDetails = [];
      for (const factory of this.factories) {
        const location = this.locations.find(loc => loc.id === factory.locationId);
        const address = this.addresses.find(addr => addr.id === location?.addressId);
        factoriesWithDetails.push({
          ...factory,
          location: location || {},
          address: address || {}
        });
      }
      return factoriesWithDetails;
    }
    }
}



</script>

<style>
body {
    margin: 0;
}

.profile-button {
    background-color: #4caf50;
    color: white;
    padding: 15px 32px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    cursor: pointer;
    border: none;
    border-radius: 4px;
    position: absolute;
    top: -2px;
    right: 580px;
  }
.login-button {
    background-color: #4caf50;
    color: white;
    padding: 15px 32px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    cursor: pointer;
    border: none;
    border-radius: 4px;
    position: absolute;
    top: -2px;
    right: 20px;
  }
  .usersview-button {
      background-color: #4caf50;
      color: white;
      padding: 15px 32px;
      text-align: center;
      text-decoration: none;
      display: inline-block;
      font-size: 16px;
      cursor: pointer;
      border: none;
      border-radius: 4px;
      position: absolute;
      top: -2px;
      right: 375px;
    }
  
.createfactory-button {
    background-color: #4caf50;
    color: white;
    padding: 15px 32px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    cursor: pointer;
    border: none;
    border-radius: 4px;
    position: absolute;
    top: -2px;
    right: 165px;
  }
.top-section {
    background-color: #1e2952;
    padding: 40px;
    color: white;
}

.factory-cards {
    display: flex;
    flex-wrap: wrap;
    gap: 50px;
    margin: 50px;
  }

  .factory-card-link {
    text-decoration: none;
    color: inherit;
  }
  
  .factory-cards .factory-card {
    width: 300px;
    margin: 10px;
  }
</style>