<template>
  <div>
    I'm exams!
  </div>
</template>

<script>
  import { mapGetters } from 'vuex'
  export default {
    name: 'Exams',
    computed: {
      ...mapGetters([
        'exams'
      ]),
    },
  }
</script>