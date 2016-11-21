<template>
  <div>
    I'm analytics!
  </div>
</template>

<script>
  import { mapGetters } from 'vuex'
  export default {
    name: 'Classes',
    data() {
      return {
        exam: 0
      }
    },
    computed: {
      ...mapGetters([
        'scores',
        'studentNames',
        'examNames'
      ]),
      displayedScores() {
        return this.scores.filter(
          x => x.exam_id === this.exam_id
        )
      },
      meanScore() {

      }
    },
  }
</script>