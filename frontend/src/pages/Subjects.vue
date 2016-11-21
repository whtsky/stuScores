<template>
  <div>
    I'm subjects!
  </div>
</template>

<script>
  import { mapGetters } from 'vuex'
  export default {
    name: 'Subjects',
    computed: {
      ...mapGetters([
        'subjects'
      ]),
    },
  }
</script>