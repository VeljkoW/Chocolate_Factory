<template>
    <div>
      <div class="top-section-home">
        <div class="left-buttons">

          <div class="dropdown">
            <button class="dropdown-button" @click="toggleDropdown">Search</button>
            <div v-if="dropdownVisible" class="dropdown-content">
              <label for="factoryName">Factory Name:</label>
              <input type="text" id="factoryName" v-model="searchFactoryName" />
  
              <label for="chocolateName">Chocolate Name:</label>
              <input type="text" id="chocolateName" v-model="searchChocolateName" />
  
              <label for="location">Location:</label>
              <input type="text" id="location" v-model="searchLocation" />
  
              <label for="averageGrade">Average Grade:</label>
              <input type="number" min="1" max="5" step="0.1" id="averageGrade" v-model="searchAverageGrade" />
  
              <button @click="searchFactories">Search</button>
            </div>
          </div>

          <div class="dropdown">
            <button class="dropdown-button2" @click="toggleSortDropdown">Sort</button>
            <div v-if="sortDropdownVisible" class="dropdown-content">
              <button @click="sortBy('name')">Factory Name</button>
              <button @click="sortBy('location')">Location</button>
              <button @click="sortBy('grade')">Average Grade</button>
            </div>
          </div>

          <div class="dropdown">
            <button class="dropdown-button3" @click="toggleFilterDropdown">Filter</button>
            <div v-if="filterDropdownVisible" class="dropdown-content">
              <label for="chocolateVariety">Chocolate Variety:</label>
              <select id="chocolateVariety" v-model="filterChocolateVariety">
                <option value="">Select Variety</option>
                <option v-for="variety in varieties" :key="variety" :value="variety">{{ variety }}</option>
              </select>
  
              <label for="chocolateType">Chocolate Type:</label>
              <select id="chocolateType" v-model="filterChocolateType">
                <option value="">Select Type</option>
                <option value="Milk">Milk</option>
                <option value="White">White</option>
                <option value="Dark">Dark</option>
                <option value="Liquor">Liquor</option>
                <option value="Bittersweet">Bittersweet</option>
                <option value="Ruby">Ruby</option>
                <option value="Cocoa powder">Cocoa powder</option>
              </select>
  
              <label for="openFactories">Open Factories:</label>
              <select id="openFactories" v-model="filterOpenFactories">
                <option value="">Select Status</option>
                <option value="Open">Open</option>
                <option value="Closed">Closed</option>
              </select>
  
              <button @click="filterFactories">Apply Filters</button>
            </div>
          </div>

          <div>
            <button class="reset-filters-button" @click="resetFilters">Reset</button>
          </div>

        </div>

        <div class="right-buttons">
          <button class="profile-button" @click="navigateToProfile">My profile</button>
          <button class="usersview-button" @click="navigateToUsers">View all users</button>
          <button class="createfactory-button" @click="navigateToCreateFactory">Create Factory</button>
          <button class="login-button" @click="navigateToLogin">Log in</button>
        </div>
      </div>
  
      <div class="factory-cards">
        <div v-for="factory in factoriesWithDetailsComputed" :key="factory.id" class="factory-card-link" @click="navigateToFactory(factory.id)">
          <FactoryCard :factory="factory" :address="factory.address" class="factory-card"/>
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
            varieties: [],
            factoriesWithDetails: [],
            dropdownVisible: false,
            sortDropdownVisible: false,
            filterDropdownVisible: false,
            searchFactoryName: '',
            searchAverageGrade: '',
            searchChocolateName: '',
            searchLocation: '',
            filterChocolateVariety: '',
            filterChocolateType: '',
            filterOpenFactories: '',
            filteredfactories: [],
            sortDirection: 'asc'
        }
    },
    mounted()
    {
        this.getfactories();
        this.getlocations();
        this.getAddresses();
        this.getVarieties();
    },
    methods: 
    {
        getfactories()
        {
            axios.get('http://localhost:8080/WebShopAppREST/rest/factory').then(response => {
                this.factories = response.data;
                this.filteredfactories = this.factories;
                console.log('Factories:',this.factories);
            }).catch(error =>
                {
                    console.log('Error fetching factories:',error);
                    alert(error.response.data);
                }
            );
        },
        getlocations()
        {
            axios.get('http://localhost:8080/WebShopAppREST/rest/location').then(response => {
                this.locations = response.data;
                console.log('Locations:',this.locations);
            }).catch(error =>{
                console.error('Error fetching locations:',error);
                alert(error.response.data);
            });
        },
        getAddresses()
        {
            axios.get('http://localhost:8080/WebShopAppREST/rest/address').then(response => {
                this.addresses = response.data;
                console.log('Adresses', this.addresses);
            }).catch(error => {
                console.error('Error fetching addresses:',error);
                alert(error.response.data);
            });
        },
        getVarieties()
        {
            axios.get('http://localhost:8080/WebShopAppREST/rest/chocolate/getAllVarieties').then(response => {
                this.varieties = response.data;
                console.log('Varieties', this.varieties);
            }).catch(error => {
                console.error('Error fetching varieties:',error);
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
        },
        toggleDropdown() 
        {
            this.dropdownVisible = !this.dropdownVisible;
        },
        toggleSortDropdown()
        {
            this.sortDropdownVisible = !this.sortDropdownVisible;
        },
        toggleFilterDropdown()
        {
            this.filterDropdownVisible = !this.filterDropdownVisible;
        },
        searchFactories() 
        {
            this.filteredfactories = this.factories.filter(factory => {
                const matchesName = this.searchFactoryName === '' || factory.name.toLowerCase().includes(this.searchFactoryName.toLowerCase());
                const matchesGrade = this.searchAverageGrade === '' || factory.grade == this.searchAverageGrade;
                return matchesName && matchesGrade;
            });
            if(this.searchChocolateName != '')
            {
                this.filteredfactories.forEach(factory => {
                    axios.get('http://localhost:8080/WebShopAppREST/rest/chocolate/getByFactoryId?factoryId=' + factory.id).then(response => {
                            const chocolates = response.data;
                            const matchedChocolates = chocolates.filter(chocolate => {
                                return chocolate.name.toLowerCase().includes(this.searchChocolateName.toLowerCase());
                            });

                    if (matchedChocolates.length > 0) 
                    { 
                        //this.filteredfactories.push(factory);
                    }
                    else
                    {
                        const index = this.filteredfactories.indexOf(factory);
                        if (index !== -1) 
                        {
                        this.filteredfactories.splice(index, 1);
                        }
                    }   
                    }).catch(error => {
                            console.error('Error fetching chocolates:', error);
                            alert(error.response.data);
                        });
                });
            }
            if (this.searchLocation !== '') 
            {
                this.filteredfactories.forEach(factory => 
                {
                    axios.get('http://localhost:8080/WebShopAppREST/rest/address/getPlaceByLocationId?locationId=' + factory.locationId).then(response => 
                    {
                        const place = response.data;
                        if (!place.toLowerCase().includes(this.searchLocation.toLowerCase())) 
                        {
                            const index = this.filteredfactories.indexOf(factory);
                            if (index !== -1) 
                            {
                            this.filteredfactories.splice(index, 1);
                            }
                        }
                        }).catch(error => 
                        {
                        console.error('Error fetching place:', error);
                        alert(error.response.data);
                        });
                });
            }
        },
        async sortBy(key) 
        {
            this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';

            if (key === 'location') //maps out places to each factory because sorting bugs out if the axios is called in the middle of it
            {
            await Promise.all(this.filteredfactories.map(factory => 
            {
                return this.fetchPlace(factory).then(place => 
                {
                    factory.currentPlace = place.toLowerCase();
                });
            }));
            }   

            this.filteredfactories.sort((a, b) => 
            {
                const fieldA = this.getFieldValue(a, key);
                const fieldB = this.getFieldValue(b, key);

                let comparison = 0;
                if (fieldA > fieldB) {
                    comparison = 1;
                } else if (fieldA < fieldB) {
                    comparison = -1;
                }

                return this.sortDirection === 'desc' ? comparison * -1 : comparison;
            });
        },
        getFieldValue(factory, key) 
        {
            switch (key) 
            {
                case 'name':
                    return factory.name.toLowerCase();
                case 'location':
                    return factory.currentPlace || '';
                case 'grade':
                    return factory.grade;
                default:
                    return '';
            }
        },
        fetchPlace(factory) 
        {
            return axios.get('http://localhost:8080/WebShopAppREST/rest/address/getPlaceByLocationId?locationId=' + factory.locationId).then(response => response.data).catch(error => 
            {
                console.error('Error fetching place:', error);
                alert(error.response.data);
                return '';
            });
        },
        filterFactories()
        {
            this.filteredfactories = this.factories.filter(factory => {
                const matchesOpenStatus = this.filterOpenFactories === '' || factory.status === this.filterOpenFactories;
                return matchesOpenStatus;
            });
            if(this.filterChocolateVariety != '')
            {
                this.filteredfactories.forEach(factory => {
                    axios.get('http://localhost:8080/WebShopAppREST/rest/chocolate/getByFactoryId?factoryId=' + factory.id).then(response => {
                            const chocolates = response.data;
                            const matchedChocolates = chocolates.filter(chocolate => {
                                return chocolate.variety.toLowerCase().includes(this.filterChocolateVariety.toLowerCase());
                            });

                    if (matchedChocolates.length > 0) 
                    { 
                        //this.filteredfactories.push(factory);
                    }
                    else
                    {
                        const index = this.filteredfactories.indexOf(factory);
                        if (index !== -1) 
                        {
                        this.filteredfactories.splice(index, 1);
                        }
                    }   
                    }).catch(error => {
                            console.error('Error fetching chocolates:', error);
                            alert(error.response.data);
                        });
                });
            }
            if(this.filterChocolateType != '')
            {
                this.filteredfactories.forEach(factory => {
                    axios.get('http://localhost:8080/WebShopAppREST/rest/chocolate/getByFactoryId?factoryId=' + factory.id).then(response => {
                            const chocolates = response.data;
                            const matchedChocolates = chocolates.filter(chocolate => {
                                return chocolate.type.toLowerCase().includes(this.filterChocolateType.toLowerCase());
                            });

                    if (matchedChocolates.length > 0) 
                    { 
                        //this.filteredfactories.push(factory);
                    }
                    else
                    {
                        const index = this.filteredfactories.indexOf(factory);
                        if (index !== -1) 
                        {
                        this.filteredfactories.splice(index, 1);
                        }
                    }   
                    }).catch(error => {
                            console.error('Error fetching chocolates:', error);
                            alert(error.response.data);
                        });
                });
            }
        },
        resetFilters()
        {
            this.searchFactoryName = '';
            this.searchAverageGrade = '';
            this.searchChocolateName = '';
            this.searchLocation = '';
            this.filterChocolateVariety = '';
            this.filterChocolateType = '';
            this.filterOpenFactories = '';
            this.filteredfactories = this.factories;
        }
    },
    computed: {
    factoriesWithDetailsComputed() {
      const factoriesWithDetails = [];
      for (const factory of this.filteredfactories) {
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

.top-section-home {
    background-color: #1e2952;
    padding: 10px 40px;
    color: white;
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 87px;
}

.dropdown {
    position: relative;
    display: inline-block;
    margin-right: 20px;
}

.dropdown-button,
.dropdown-button2,
.dropdown-button3,
.reset-filters-button {
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
}

.reset-filters-button {
    background-color: red;
}

.reset-filters-button:hover {
    background-color: darkred;
}

.dropdown-content {
    display: flex;
    flex-direction: column;
    background-color: #f9f9f9;
    box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
    padding: 12px 16px;
    position: absolute;
    z-index: 1;
    top: 60px;
    left: 0;
    width: 250px;
}

.dropdown-content input {
    margin-bottom: 10px;
    padding: 5px;
    width: 200px;
}

.button-group {
    display: flex;
    gap: 15px;
}

.profile-button,
.login-button,
.usersview-button,
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
}

.left-buttons {
    display: flex;
    align-items: center;
}

.right-buttons {
    display: flex;
    gap: 15px;
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

.dropdown-content label {
    color: black;
}

.factory-cards .factory-card {
    width: 300px;
    margin: 10px;
}
</style>