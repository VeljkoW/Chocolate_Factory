<template>
    <div>
        <div class="top-section">
          <h1>{{ name }}</h1>
          <img :src="logoImagePath" alt="Factory Logo" class="factory-logo" />
        </div>
        <div class="factory-details">
          <h2>Factory Details</h2>
          <p><strong>Opening Time :</strong> {{ openingTime }}</p>
          <p><strong>Closing Time:</strong> {{ closingTime }}</p>
          <p><strong>Status:</strong> {{ status }}</p>
          <p><strong>Grade:</strong> {{ grade }}</p>
          
          <h2>Location Details</h2>
          <p><strong>Latitude:</strong> {{ latitude }}</p>
          <p><strong>Longitude:</strong> {{ longitude }}</p>
          
          <h2>Address</h2>
          <p><strong>City:</strong> {{ city }}</p>
          <p><strong>Street:</strong> {{ street }}</p>
          <p><strong>Number:</strong> {{ number }}</p>
          <p><strong>Postal Code:</strong> {{ postalCode }}</p>
        </div>
      </div>
</template>
<script>
import axios from 'axios';

export default {
    name: 'FactoryPage',
    data()
    {
        return {
            id: 0,
            locationId: 0,
            addressId: 0,
            name: '',
            openingTime: '',
            closingTime: '',
            status: '',
            latitude: 0,
            longitude: 0,
            city: '',
            street: '',
            postalCode: '',
            number: 0,
            logoImagePath: '',
            grade : 0,
        }
    },
    mounted()
    {
        this.getFactory();
    },
    methods:
    {
        getFactory()
        {
            let id = this.$route.params.id;
            if(id == null)
            {
                alert('factory not found');
                return;
            }

            axios.get('http://localhost:8080/WebShopAppREST/rest/factory/GetById?id=' + id).then(response => {
                this.id = response.data.id;
                this.name = response.data.name;
                this.openingTime = response.data.openingTime;
                this.closingTime = response.data.closingTime;
                this.status = response.data.status;
                this.locationId = response.data.locationId;
                this.logoImagePath = response.data.logoImagePath;
                this.grade = response.data.grade;
                this.getLocation();
            }).catch(error => {
                alert(error.response.data);
            });

        },
        getLocation()
        {
            if(this.locationId === 0)
            {
                alert('location not found' + this.locationId);
                return;
            }
            axios.get('http://localhost:8080/WebShopAppREST/rest/location/GetById?id=' + this.locationId).then(response => {
                this.latitude = response.data.latitude;
                this.longitude = response.data.longitude;
                this.addressId = response.data.addressId;
                this.getAddress();
            }).catch(error => {
                alert(error.response.data);
            });
        },
        getAddress()
        {
            if(this.addressId === 0)
            {
                alert('address not found');
                return;
            }
            axios.get('http://localhost:8080/WebShopAppREST/rest/address/GetById?id=' + this.addressId).then(response => {
                this.city = response.data.place;
                this.street = response.data.street;
                this.number = response.data.number;
                this.postalCode = response.data.postalCode;
            }).catch(error => {
                alert(error.response.data);
            });
        }
    }
}

</script>
<style>

.top-section {
    background-color: #1e2952;
    padding: 40px;
    color: white;
    text-align: center;
  }
  
  .factory-logo {
    max-width: 100%;
    height: auto;
    margin-top: 20px;
  }
  
  .factory-details {
    padding: 20px;
    margin: 20px;
    background-color: #f9f9f9;
    border-radius: 8px;
  }
  
  .factory-details h2 {
    margin-top: 0;
  }
  
  .factory-details p {
    margin: 5px 0;
  }
</style>