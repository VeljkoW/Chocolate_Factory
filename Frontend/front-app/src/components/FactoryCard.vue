<template>
    <div v-if="factory && address" class="card-container">
      <div class="card">
        <img :src="factory.logoImagePath" alt="Factory Logo" class="factory-image"/>
        <div class="container">
          <h2>{{ factory.name }}</h2>
          <p v-if="address">{{ address.place }}, {{ address.street }} {{ address.number }}</p>
          <p v-if="factory.grade">Average Grade: {{ factory.grade }}</p>
        </div>
      </div>
    </div>
    <div v-else>
      Loading...
    </div>
</template>
<script>
export default {
    name: 'FactoryCard',
    props: {
        factory: Object,
        address: Object
        
    }
}
</script>

<style>

.card {
    box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
    transition: 0.3s;
    border-radius: 5px;
}
.card:hover {
    box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2);
    
}

.card.container {
    padding: 2px 16px;
    max-width: 400px;
}

.container {
    padding:2px 16px;
}


.factory-image {
    width: 100px;
    height: 100px;
    border-radius: 5px 5px 0 0;
}

</style>