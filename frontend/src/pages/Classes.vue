<template>
  <div>
    I'm classes!
  </div>
</template>

<script>
  import { mapGetters } from 'vuex'
  export default {
    name: 'Classes',
    computed: {
      ..mapGetters({
        'classes'
      }),
    },
  }
</script>