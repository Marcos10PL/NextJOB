<template>
  <UApp>
    <LayoutHeader />

    <UMain class="min-h-[calc(100vh-12rem)]">
      <UContainer>
        <slot />
      </UContainer>
    </UMain>

    <LayoutFooter />
  </UApp>
</template>
