<template>
  <UApp>
    <LayoutDefaultHeader />

    <UMain>
      <UContainer class="py-6">
        <slot />
      </UContainer>
    </UMain>

    <LayoutDefaultFooter />
  </UApp>
</template>
