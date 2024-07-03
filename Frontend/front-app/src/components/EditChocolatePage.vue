<template>
    <div>
        <div class="top-section">
            <h1>Chocolate</h1>
            <button class="back-button" @click="goBack">Go back</button>
        </div>
        <div class="table-container-chocolateedit">
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
                        Status :
                    </td>
                    <td>
                        <select v-model="status" id="statusTextbox">
                            <option>Available</option>
                            <option>Unavailable</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        Stock :
                    </td>
                    <td>
                        <input type="number" v-model="stock" id="stockTextbox"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        Image :
                    </td>
                    <td>
                        <input type="text" v-model="image" id="imageTextbox"/>
                    </td>
                </tr>
            </table>
            <button @click="EditChocolate">Confirm</button>
        </div>
    </div>
</template>
<script>
import axios from 'axios';

export default {
    name: 'EditChocolatePage',
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
            stock: 0,
        }
    },
    mounted()
    {
        this.getChocolate();
    },
    methods: 
    {
        getChocolate()
        {
            let chocolateId = this.$route.params.id;
            if(chocolateId == null)
            {
                alert('factory not found');
                return;
            }
            axios.get('http://localhost:8080/WebShopAppREST/rest/chocolate/getById?id=' + chocolateId).then(response => {
                this.name = response.data.name;
                this.price = response.data.price;
                this.variety = response.data.variety;
                this.type = response.data.type;
                this.mass = response.data.mass;
                this.description = response.data.description;
                this.status = response.data.status;
                this.image = response.data.imagePath;
                this.stock = response.data.stock;
                this.factoryId = response.data.factoryId;
            }).catch(error => {
                alert(error.response.data);
            });
        },
        EditChocolate()
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
            if(this.stock <= 0)
            {
                document.getElementById('stockTextbox').classList.add('errorClass');
                error=true;
            }
            else
            {
                document.getElementById('stockTextbox').classList.remove('errorClass');
            }
            if(this.status == '')
            {
                document.getElementById('statusTextbox').classList.add('errorClass');
                error=true;
            }
            else
            {
                document.getElementById('statusTextbox').classList.remove('errorClass');
            }
            if(error)
            {
                return;
            }
            let chocolateId = this.$route.params.id;
            if(chocolateId == null)
            {
                alert('factory not found');
                return;
            }

            axios.post('http://localhost:8080/WebShopAppREST/rest/chocolate/update', {
                id: chocolateId,
                name: this.name,
                price: this.price,
                variety: this.variety,
                factoryId: this.factoryId,
                type: this.type,
                mass: this.mass,
                description: this.description,
                imagePath: this.image,
                status: this.status,
                stock: this.stock,
            }).then(response => {
                this.$router.push("/chocolate/" + chocolateId);
            }).catch(error => {
                alert(error.response.data);
            });

        },
        goBack()
        {
            let chocolateId = this.$route.params.id;
            if(chocolateId == null)
            {
                alert('factory not found');
                return;
            }
            this.$router.push("/chocolate/" + chocolateId);
        }
    }

}

</script>
<style>

.table-container-chocolateedit {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: calc(100vh - 40px - 40px);
  }
</style>