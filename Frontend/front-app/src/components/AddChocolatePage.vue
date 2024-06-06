<template>
    <div>
        <div class="top-section">
            <h1>Add chocolate to factory</h1>
            <button class="back-button" @click="goBack">Go back</button>
        </div>
        <div class="table-container">
            <table>
                <tr>
                    <td>
                        *Name :
                    </td>
                    <td>
                        <input type="text" v-model="name" id="nameTextbox"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        *Price :
                    </td>
                    <td>
                        <input type="number" v-model="price" id="priceTextbox"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        *Variety :
                    </td>
                    <td>
                        <input type="text" v-model="variety" id="varietyTextbox"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        *Type :
                    </td>
                    <td>
                        <select v-model="type" id="typeTextbox">
                            <option>Milk</option>
                            <option>White</option>
                            <option>Dark</option>
                            <option>Liquor</option>
                            <option>Bittersweet</option>
                            <option>Ruby</option>
                            <option>Cocoa powder</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        *Mass :
                    </td>
                    <td>
                        <input type="number" v-model="mass" id="massTextbox"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        *Description :
                    </td>
                    <td>
                        <input type="text" v-model="description" id="descriptionTextbox"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        *Image :
                    </td>
                    <td>
                        <input type="text" v-model="image" id="imageTextbox"/>
                    </td>
                </tr>
            </table>
            <button @click="addChocolate">Add Chocolate</button>
        </div>
    </div>
</template>
<script>
import axios from 'axios';

export default {
    name: 'AddChocolatePage',
    data()
    {
        return {
            name: '',
            price: 0,
            variety: '',
            type: 'Milk',
            mass: 0,
            description: '',
            status: 'Unavailable',
            image: '',
            factoryId: 0,
            isDeleted: false,
        }
    },
    methods:
    {
        addChocolate()
        {
            let error = false;
            if(this.name == '')
            {
                document.getElementById('nameTextbox').classList.add('errorClass');
                error=true;
            }
            else
            {
                document.getElementById('nameTextbox').classList.remove('errorClass');
            }
            if(this.price <= 0)
            {
                document.getElementById('priceTextbox').classList.add('errorClass');
                error=true;
            }
            else
            {
                document.getElementById('priceTextbox').classList.remove('errorClass');
            }
            if(this.variety == '')
            {
                document.getElementById('varietyTextbox').classList.add('errorClass');
                error=true;
            }
            else
            {
                document.getElementById('varietyTextbox').classList.remove('errorClass');
            }
            if(this.type == '')
            {
                document.getElementById('typeTextbox').classList.add('errorClass');
                error=true;
            }
            else
            {
                document.getElementById('typeTextbox').classList.remove('errorClass');
            }
            if(this.mass <= 0)
            {
                document.getElementById('massTextbox').classList.add('errorClass');
                error=true;
            }
            else
            {
                document.getElementById('massTextbox').classList.remove('errorClass');
            }
            if(this.description == '')
            {
                document.getElementById('descriptionTextbox').classList.add('errorClass');
                error=true;
            }
            else
            {
                document.getElementById('descriptionTextbox').classList.remove('errorClass');
            }
            if(this.image == '')
            {
                document.getElementById('imageTextbox').classList.add('errorClass');
                error=true;
            }
            else
            {
                document.getElementById('imageTextbox').classList.remove('errorClass');
            }

            if(error)
            {
                return;
            }
            let FactoryId = this.$route.params.id;
            if(FactoryId == null)
            {
                alert('factory not found');
                return;
            }
            this.factoryId = FactoryId;
            axios.put('http://localhost:8080/WebShopAppREST/rest/chocolate', {
                name: this.name,
                price: this.price,
                variety: this.variety,
                factoryId: FactoryId,
                type: this.type,
                mass: this.mass,
                description: this.description,
                imagePath: this.image,
                status: this.status,
                stock: 0,
                isDeleted: false,
            }).then(response => {
                this.$router.push("/factory/" + this.factoryId);
            }).catch(error => {
                alert('error.response.data');
            });
        },
        goBack()
        {
            let FactoryId = this.$route.params.id;
            if(FactoryId == null)
            {
                alert('factory not found');
                return;
            }
            this.$router.push("/factory/" + FactoryId);
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

.errorClass {
  background-color: red;
}

.table-container {
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

input[type="text"], input[type="number"], select {
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