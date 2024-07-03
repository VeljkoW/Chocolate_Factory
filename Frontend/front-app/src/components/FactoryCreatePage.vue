<template>
    <div>
        <div class="top-section">
            <h1>Create a New Factory</h1>
            <button class="back-button" @click="goBack">Go back</button>
        </div>
        <div class="table-container">
            <table>
                <tr>
                    <td>*Factory Name :</td>
                    <td><input type="text" v-model="factoryName" id="factoryNameTextbox"/></td>
                </tr>
                <tr>
                    <td>*Latitude :</td>
                    <td><input type="number" step="0.000001" v-model="latitude" id="latitudeTextbox"/></td>
                </tr>
                <tr>
                    <td>*Longitude :</td>
                    <td><input type="number" step="0.000001" v-model="longitude" id="longitudeTextbox"/></td>
                </tr>
                <tr>
                    <td>*Street :</td>
                    <td><input type="text" v-model="street" id="streetTextbox"/></td>
                </tr>
                <tr>
                    <td>*Number :</td>
                    <td><input type="text" v-model="number" id="numberTextbox"/></td>
                </tr>
                <tr>
                    <td>*Place :</td>
                    <td><input type="text" v-model="place" id="placeTextbox"/></td>
                </tr>
                <tr>
                    <td>*Postal Code :</td>
                    <td><input type="number" v-model="postalCode" id="postalCodeTextbox"/></td>
                </tr>
                <tr>
                    <td>*Opening Time :</td>
                    <td><input type="time" v-model="openingTime" id="openingTimeTextbox"/></td>
                </tr>
                <tr>
                    <td>*Closing Time :</td>
                    <td><input type="time" v-model="closingTime" id="closingTimeTextbox"/></td>
                </tr>
                <tr>
                    <td>*Logo Image Path :</td>
                    <td><input type="text" v-model="logoImagePath" id="logoImagePathTextbox"/></td>
                </tr>
                <tr>
                    <td>*Manager :</td>
                    <td>
                        <select v-model="managerId" id="managerIdTextbox">
                            <option v-for="manager in managers" :key="manager.id" :value="manager.id">
                                {{ manager.name }}
                                {{ manager.surname }}
                                {{", " + manager.uloga }}
                            </option>
                        </select>
                    </td>
                </tr>
            </table>
            <button @click="createAddress">Create Factory</button>
        </div>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    name: 'CreateFactoryPage',
    data() {
        return {
            factoryName: '',
            latitude: 0,
            longitude: 0,
            street: '',
            number: '',
            place: '',
            postalCode: '',
            openingTime: '',
            closingTime: '',
            logoImagePath: '',
            managerId: null,
            managers: [],
            addressId: -1,
            locationId: -1,
            factoryId: -1
        }
    },
    methods: {
        async fetchManagers() {
            try {
                const response = await axios.get('http://localhost:8080/WebShopAppREST/rest/user/managers');
                this.managers = response.data;
            } catch (error) {
                console.error('Error fetching managers:', error);
                alert('Failed to load managers.');
            }
        },
        createAddress() {
            let error = false;

            if (this.factoryName === '') {
                document.getElementById('factoryNameTextbox').classList.add('errorClass');
                error = true;
            } else {
                document.getElementById('factoryNameTextbox').classList.remove('errorClass');
            }

            if (this.latitude === 0) {
                document.getElementById('latitudeTextbox').classList.add('errorClass');
                error = true;
            } else {
                document.getElementById('latitudeTextbox').classList.remove('errorClass');
            }

            if (this.longitude === 0) {
                document.getElementById('longitudeTextbox').classList.add('errorClass');
                error = true;
            } else {
                document.getElementById('longitudeTextbox').classList.remove('errorClass');
            }

            if (this.street === '') {
                document.getElementById('streetTextbox').classList.add('errorClass');
                error = true;
            } else {
                document.getElementById('streetTextbox').classList.remove('errorClass');
            }

            if (this.number === '') {
                document.getElementById('numberTextbox').classList.add('errorClass');
                error = true;
            } else {
                document.getElementById('numberTextbox').classList.remove('errorClass');
            }

            if (this.place === '') {
                document.getElementById('placeTextbox').classList.add('errorClass');
                error = true;
            } else {
                document.getElementById('placeTextbox').classList.remove('errorClass');
            }

            if (this.postalCode === '') {
                document.getElementById('postalCodeTextbox').classList.add('errorClass');
                error = true;
            } else {
                document.getElementById('postalCodeTextbox').classList.remove('errorClass');
            }

            if (this.openingTime === '') {
                document.getElementById('openingTimeTextbox').classList.add('errorClass');
                error = true;
            } else {
                document.getElementById('openingTimeTextbox').classList.remove('errorClass');
            }

            if (this.closingTime === '') {
                document.getElementById('closingTimeTextbox').classList.add('errorClass');
                error = true;
            } else {
                document.getElementById('closingTimeTextbox').classList.remove('errorClass');
            }

            if (this.logoImagePath === '') {
                document.getElementById('logoImagePathTextbox').classList.add('errorClass');
                error = true;
            } else {
                document.getElementById('logoImagePathTextbox').classList.remove('errorClass');
            }

            if (this.managerId === null) {
                document.getElementById('managerIdTextbox').classList.add('errorClass');
                error = true;
            } else {
                document.getElementById('managerIdTextbox').classList.remove('errorClass');
            }

            if (error) {
                return;
            }
            let addressId = -1;
            axios.put('http://localhost:8080/WebShopAppREST/rest/address', {
                id: -1,
                street: this.street,
                number: this.number,
                place: this.place,
                postalCode: this.postalCode
            }).then(response => {
                this.addressId = response.data;
                this.createLocation();
            }
            ).catch(error => {
                alert(error.response.data);
            });
        },
        createLocation()
        {

            if(this.addressId == -1)
            {
                alert('address not obtained properly');
                return;
            }
            let locationId = -1;
            axios.put('http://localhost:8080/WebShopAppREST/rest/location', {
                id: -1,
                latitude: this.latitude,
                longitude: this.longitude,
                addressId: this.addressId
            }).then(response =>{
                this.locationId = response.data;
                this.createFactory();
            }
            ).catch(error => {
                alert(error.response.data);
            });

        },
        createFactory()
        {
            if(this.locationId == -1)
            {
                alert('location not obtained properly');
                return;
            }

            axios.put('http://localhost:8080/WebShopAppREST/rest/factory', {
                name: this.factoryName,
                openingTime: this.openingTime,
                closingTime: this.closingTime,
                logoImagePath: this.logoImagePath,
                locationId: this.locationId,
                status: "closed",
                grade: 1,
            }).then(response => {
                this.factoryId = response.data;
                this.setManager();
            }).catch(error => {
                alert('Error creating factory');
            });

        },
        setManager()
        {
            if(this.managerId == null)
            {
                alert('failed to fetch manager');
                return;
            }
            if(this.factoryId == -1)
            {
                alert('failed to fetch factory id')
                return;
            }
            axios.put('http://localhost:8080/WebShopAppREST/rest/user/editManager?managerId=' + this.managerId + '&factoryId=' + this.factoryId).then(response => {
                this.$router.push("/");
            }).catch(error => {
                alert('Error setting manager')
            });
        },
        goBack() {
            this.$router.push("/");
        }
    },
    mounted() {
        this.fetchManagers();
    }
}
</script>

<style>
.top-section {
    background-color: #1e2952;
    padding: 40px;
    color: white;
    text-align: center;
    position: relative;
    z-index: 1;
}

.errorClass {
    background-color: red;
}

.table-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    margin-top: 100px;
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

table th, table td {
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

input[type="text"], input[type="number"], input[type="time"], select {
    width: 100%;
    padding: 10px;
    margin: 8px 0;
    box-sizing: border-box;
    border: 1px solid #ccc;
    border-radius: 4px;
}

button {
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
    margin-top: 20px;
}

button:hover {
    background-color: #45a049;
}
</style>