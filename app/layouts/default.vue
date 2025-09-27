<template>
  <UApp>
    <LayoutDefaultHeader />

    <UMain class="min-h-[calc(100vh-14rem)] my-4">
      <UContainer>
        <slot />
      </UContainer>
    </UMain>

    <LayoutDefaultFooter />
  </UApp>
</template>
