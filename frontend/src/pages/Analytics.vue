<template>
  <div>
    I'm analytics!
  </div>
</template>

<script>
  import { mapGetters } from 'vuex'
  export default {
    name: 'Classes',
    computed: {
      ...mapGetters([
        'scores',
        'studentNames',
        'examNames'
      ]),
    },
  }
</script>