<template>
  <div v-if="factory && address" class="card-container">
    <div class="card">
      <div class="image-container">
        <img :src="factory.logoImagePath" alt="Factory Logo" class="factory-image"/>
      </div>
      <div class="container">
        <h2 class="factory-name">{{ factory.name }}</h2>
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
  max-width: 300px;
  margin: 0 auto; 
}

.card:hover {
  box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 0.2);
}

.card-container {
  padding: 2px 16px;
}

.image-container {
  width: 100%;
  height: 200px;
  overflow: hidden;
  border-radius: 5px 5px 0 0;
}

.factory-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.container {
  padding: 16px;
  text-align: center;
}

.factory-name {
  font-size: 1.2em;
  margin: 0;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>