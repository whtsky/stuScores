<template>
  <div>
    I'm scores!
  </div>
</template>

<script>
  import { mapGetters } from 'vuex'
  export default {
    name: 'Scores',
    computed: {
      ..mapGetters({
        'scores',
        'studentNames',
        'examNames'
      }),
    },
  }
</script>